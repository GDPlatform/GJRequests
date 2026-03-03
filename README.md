# gjrequest.js

## 🇺🇸 English (U.S.)

> A lightweight Node.js SDK for interacting with the **Geometry Dash API** (GDPlatform-powered).
> Handles login, reading/sending messages, and fetching level scores using `gjp2`.

### Features

* Login with `accountID` + password (generates `gjp2`)
* Read inbox or sent messages
* Send messages (Base64-encoded)
* Fetch level scores
* Generic endpoint requests via `requestEndpoint()`

### Installation

```bash
npm install gjrequest.js
```

> Or copy the module locally if not published.

### Usage

```js
require("dotenv").config();
const { Client } = require("gjrequest.js");

(async () => {
    const client = new Client();

    // Login securely via environment variables
    await client.login({
        accountID: process.env.GD_ACCOUNT_ID,
        password: process.env.GD_PASSWORD
    });

    // Read inbox messages
    const inbox = await client.readMessages();
    console.log(inbox);

    // Send a message
    const sent = await client.sendMessage(
        29294657,
        "Hello World",
        "This is a test message!"
    );
    console.log(sent);

    // Get level scores
    const scores = await client.getLevelScores(1234567);
    console.log(scores);

    // Generic endpoint request
    const response = await client.requestEndpoint("uploadGJMessage20.php", {
        toAccountID: 29294657,
        subject: Buffer.from("Test").toString("base64"),
        body: Buffer.from("Hello!").toString("base64")
    });
    console.log(response);
})();
```

### Variables

| Variable      | Type      | Description                                                                        |
| ------------- | --------- | ---------------------------------------------------------------------------------- |
| `accountID`   | `number`  | Your GD account ID. Set after login.                                               |
| `gjp2`        | `string`  | Generated from password via `generateGjp2()`. Required for authenticated requests. |
| `levelID`     | `number`  | The ID of the level to fetch scores from.                                          |
| `messageID`   | `number`  | The ID of a specific message to read.                                              |
| `toAccountID` | `number`  | Account ID of the user to send a message to.                                       |
| `subject`     | `string`  | Message subject (Base64-encoded automatically).                                    |
| `body`        | `string`  | Message body (Base64-encoded automatically).                                       |
| `page`        | `number`  | Page number when reading messages (0-indexed).                                     |
| `sent`        | `boolean` | Read sent messages (`true`) or inbox (`false`).                                    |
| `secret`      | `string`  | Secret key for generic endpoint requests. Default: `"Wmfd2893gb7"`.                |

### Client Callbacks

* `_requireLogin()` – Internal method. Throws if the client is not logged in.
* All methods return a `Promise` and should be `await`ed.
* `readMessages()` supports pagination with `page` and `sent` options.
* `requestEndpoint(endpoint, params, secret)` allows custom calls to GD endpoints using the client’s credentials.

### Security Notes

* **Do NOT hardcode your GD password** in public repositories.
* Use environment variables (`.env`) or other secure storage.
* `gjp2` is generated locally; no real login is sent except the hashed payload.

### References

* [GD Docs by Wyliemaster](https://wyliemaster.github.io/gddocs/)
* [Geometry Dash API Endpoints](https://www.boomlings.com/database/)

### License

MIT © SkunkPlatform

---

## 🇦🇷 Español (Argentino, es-ar)

> Un SDK ligero para Node.js para interactuar con la **API de Geometry Dash** (potenciada por GDPlatform).
> Permite iniciar sesión, leer/enviar mensajes y obtener puntuaciones de niveles usando `gjp2`.

### Funcionalidades

* Iniciar sesión con `accountID` + contraseña (genera `gjp2`)
* Leer mensajes recibidos o enviados
* Enviar mensajes (codificados en Base64)
* Obtener puntuaciones de niveles
* Solicitudes genéricas a endpoints vía `requestEndpoint()`

### Instalación

```bash
npm install gjrequest.js
```

> O copiar el módulo localmente si no está publicado.

### Uso

```js
require("dotenv").config();
const { Client } = require("gjrequest.js");

(async () => {
    const client = new Client();

    await client.login({
        accountID: process.env.GD_ACCOUNT_ID,
        password: process.env.GD_PASSWORD
    });

    const inbox = await client.readMessages();
    console.log(inbox);

    const sent = await client.sendMessage(
        29294657,
        "Hola Mundo",
        "Este es un mensaje de prueba!"
    );
    console.log(sent);

    const scores = await client.getLevelScores(1234567);
    console.log(scores);

    const response = await client.requestEndpoint("uploadGJMessage20.php", {
        toAccountID: 29294657,
        subject: Buffer.from("Prueba").toString("base64"),
        body: Buffer.from("¡Hola!").toString("base64")
    });
    console.log(response);
})();
```

### Variables

| Variable      | Tipo      | Descripción                                                                                 |
| ------------- | --------- | ------------------------------------------------------------------------------------------- |
| `accountID`   | `number`  | Tu ID de cuenta de GD. Se asigna tras el login.                                             |
| `gjp2`        | `string`  | Generado desde tu contraseña con `generateGjp2()`. Necesario para solicitudes autenticadas. |
| `levelID`     | `number`  | ID del nivel para obtener puntuaciones.                                                     |
| `messageID`   | `number`  | ID de un mensaje específico a leer.                                                         |
| `toAccountID` | `number`  | ID de la cuenta a la que enviar un mensaje.                                                 |
| `subject`     | `string`  | Asunto del mensaje (codificado automáticamente en Base64).                                  |
| `body`        | `string`  | Cuerpo del mensaje (codificado automáticamente en Base64).                                  |
| `page`        | `number`  | Número de página al leer mensajes (comienza en 0).                                          |
| `sent`        | `boolean` | Leer mensajes enviados (`true`) o bandeja de entrada (`false`).                             |
| `secret`      | `string`  | Clave secreta para solicitudes genéricas. Por defecto: `"Wmfd2893gb7"`.                     |

### Callbacks del Cliente

* `_requireLogin()` – Método interno. Lanza error si no se inició sesión.
* Todos los métodos retornan `Promise` y deben usarse con `await`.
* `readMessages()` soporta paginación con `page` y `sent`.
* `requestEndpoint(endpoint, params, secret)` permite llamadas personalizadas a endpoints usando las credenciales del cliente.

### Seguridad

* **No pongas tu contraseña de GD en repositorios públicos.**
* Usar variables de entorno (`.env`) u otro almacenamiento seguro.
* `gjp2` se genera localmente; no se envía la contraseña real, solo el hash.

### Referencias

* [GD Docs por Wyliemaster](https://wyliemaster.github.io/gddocs/)
* [Endpoints de Geometry Dash](https://www.boomlings.com/database/)

### Licencia

MIT © SkunkPlatform
