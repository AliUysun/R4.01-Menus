package com.yourcompany.menus.infrastructure.repository.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Chargeur de configuration pour la base de données
 * 
 * Charge la configuration MySQL depuis plusieurs sources :
 * 1. Fichier .env (priorité haute)
 * 2. Variables d'environnement système
 * 3. Chemin personnalisé via MENUS_DOTENV_PATH
 * 
 * Résout l'URL JDBC à partir de ces paramètres :
 * - MYSQL_HOST (host du serveur)
 * - MYSQL_PORT (port, défaut 3306)
 * - MYSQL_DATABASE (nom de la base)
 * - MYSQL_USER (utilisateur)
 * - MYSQL_PASSWORD (mot de passe)
 * OU
 * - MYSQL_URL (URL JDBC explicite)
 * 
 * @author Projet Menus
 * @version 1.0
 */
public class DbConfigLoader {

    private static final String DEFAULT_PORT = "3306";

    /**
     * Charge la configuration depuis .env et les variables d'environnement
     * 
     * @return la configuration résolue (URL JDBC, user, password)
     * @throws IllegalStateException si une configuration requise est manquante
     */
    public static DbConfig load() {
        DotEnvResult dotEnvResult = loadDotEnvWithMetadata();
        Map<String, String> values = new HashMap<>(dotEnvResult.values());
        values.putAll(System.getenv());

        String explicitUrl = values.get("MYSQL_URL");
        if (explicitUrl != null && !explicitUrl.isBlank()) {
            return new DbConfig(
                    explicitUrl,
                    require(values, "MYSQL_USER", dotEnvResult),
                    require(values, "MYSQL_PASSWORD", dotEnvResult)
            );
        }

        String host = require(values, "MYSQL_HOST", dotEnvResult);
        String port = values.getOrDefault("MYSQL_PORT", DEFAULT_PORT);
        String database = require(values, "MYSQL_DATABASE", dotEnvResult);
        String user = require(values, "MYSQL_USER", dotEnvResult);
        String password = require(values, "MYSQL_PASSWORD", dotEnvResult);
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
        return new DbConfig(url, user, password);
    }

    private static DotEnvResult loadDotEnvWithMetadata() {
        Map<String, String> env = new HashMap<>();
        DotEnvPathResolution resolution = resolveDotEnvPath();
        String source = "none";

        if (resolution.selectedPath() != null && Files.exists(resolution.selectedPath())) {
            loadDotEnvFromPath(resolution.selectedPath(), env);
            source = "file:" + resolution.selectedPath();
            return new DotEnvResult(env, source, toStrings(resolution.checkedPaths()));
        }

        if (loadDotEnvFromResource(env)) {
            source = "classpath:/.env";
        }
        return new DotEnvResult(env, source, toStrings(resolution.checkedPaths()));
    }

    private static void loadDotEnvFromPath(Path path, Map<String, String> env) {
        try {
            for (String line : Files.readAllLines(path)) {
                String trimmed = line.trim();
                if (trimmed.startsWith("#") || !trimmed.contains("=")) {
                    continue;
                }
                int split = trimmed.indexOf('=');
                String key = trimmed.substring(0, split).trim();
                String value = trimmed.substring(split + 1).trim();
                env.put(key, stripQuotes(value));
            }
        } catch (IOException ignored) {
            // Si le fichier n'est pas lisible, on se repose sur les variables d'environnement
        }
    }

    private static boolean loadDotEnvFromResource(Map<String, String> env) {
        int initialSize = env.size();
        try (InputStream inputStream = DbConfigLoader.class.getResourceAsStream("/.env")) {
            if (inputStream == null) {
                return false;
            }
            try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                StringBuilder content = new StringBuilder();
                char[] buffer = new char[1024];
                int read;
                while ((read = reader.read(buffer)) != -1) {
                    content.append(buffer, 0, read);
                }
                for (String line : content.toString().split("\\R")) {
                    String trimmed = line.trim();
                    if (trimmed.startsWith("#") || !trimmed.contains("=")) {
                        continue;
                    }
                    int split = trimmed.indexOf('=');
                    String key = trimmed.substring(0, split).trim();
                    String value = trimmed.substring(split + 1).trim();
                    env.put(key, stripQuotes(value));
                }
            }
        } catch (IOException ignored) {
            // Fallback silencieux
        }
        return env.size() > initialSize;
    }

    private static DotEnvPathResolution resolveDotEnvPath() {
        List<Path> checkedPaths = new ArrayList<>();
        String customPath = firstNonBlank(
                System.getenv("MENUS_DOTENV_PATH"),
                System.getProperty("MENUS_DOTENV_PATH")
        );
        if (customPath != null && !customPath.isBlank()) {
            Path selected = Path.of(customPath);
            checkedPaths.add(selected);
            return new DotEnvPathResolution(selected, checkedPaths);
        }

        String instanceRoot = System.getProperty("com.sun.aas.instanceRoot");
        if (instanceRoot != null && !instanceRoot.isBlank()) {
            Path domainRoot = Path.of(instanceRoot);
            checkedPaths.add(domainRoot.resolve(".env"));
            checkedPaths.add(domainRoot.resolve("config").resolve(".env"));
        }

        checkedPaths.add(Path.of(".env"));
        checkedPaths.add(Path.of(System.getProperty("user.dir", ".")).resolve(".env"));
        checkedPaths.add(Path.of(System.getProperty("user.home", ".")).resolve(".env"));

        Path selectedPath = firstExistingCandidate(checkedPaths);
        return new DotEnvPathResolution(selectedPath, checkedPaths);
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }

    private static Path firstExistingCandidate(List<Path> candidates) {
        for (Path candidate : candidates) {
            if (candidate != null && Files.exists(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    private static String require(Map<String, String> values, String key, DotEnvResult dotEnvResult) {
        String value = values.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Configuration manquante: " + key
                            + " (variable d'environnement, .env ou MENUS_DOTENV_PATH). "
                            + "Source .env=" + dotEnvResult.source()
                            + ", chemins testes=" + String.join(" ; ", dotEnvResult.checkedPaths())
            );
        }
        return value;
    }

    private static List<String> toStrings(List<Path> paths) {
        List<String> values = new ArrayList<>();
        for (Path path : paths) {
            values.add(path.toAbsolutePath().normalize().toString());
        }
        return values;
    }

    private static String stripQuotes(String value) {
        if ((value.startsWith("\"") && value.endsWith("\""))
                || (value.startsWith("'") && value.endsWith("'"))) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    public record DbConfig(String jdbcUrl, String username, String password) {
    }

    record DotEnvPathResolution(Path selectedPath, List<Path> checkedPaths) {
    }

    record DotEnvResult(Map<String, String> values, String source, List<String> checkedPaths) {
    }
}

