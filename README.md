# menus-service

Architecture simple en couches (domain / application / adapter / config) pour exposer une API REST de menus en WAR.

## Structure

- `com.yourcompany.menus.domain`: entites et logique metier (`Menu`, `MenuDomainService`)
- `com.yourcompany.menus.application`: ports et services applicatifs
- `com.yourcompany.menus.infrastructure`: adaptateurs sortants (`MenuRepository`, `UserClient`)
- `com.yourcompany.menus.rest`: API REST entrante (`MenuController`)
- `com.yourcompany.menus.config`: configuration JAX-RS

## Endpoints

Base URL (GlassFish):

- `http://localhost:8080/menus/api/menus`

Routes:

- `GET /menus`
- `POST /menus`
- `GET /menus/{id}`
- `PUT /menus/{id}`
- `DELETE /menus/{id}`

Exemple de body `POST /menus`:

```json
{
  "nom": "Menu du jour",
  "createurId": 2
}
```

## Configuration BDD MySQL

Le repository lit d'abord le fichier `.env` (non versionne), puis les variables d'environnement systeme.

Sous GlassFish, place le fichier `.env` ici (recommande):

- `<glassfish>/glassfish/domains/domain1/config/.env`

Variables attendues:

- `MYSQL_HOST`
- `MYSQL_DATABASE`
- `MYSQL_USER`
- `MYSQL_PASSWORD`



