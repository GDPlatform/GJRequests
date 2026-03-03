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

## 🇦🇷 Español (ARG)

> Un SDK liviano para Node.js que permite interactuar con la **API de Geometry Dash** (potenciada por GDPlatform).
> Permite iniciar sesión, leer/enviar mensajes y obtener puntuaciones de niveles usando `gjp2`.

### Funciones

* Iniciar sesión con `accountID` + contraseña (genera `gjp2`)
* Leer mensajes recibidos o enviados
* Enviar mensajes (codificados en Base64)
* Obtener puntuaciones de niveles
* Hacer solicitudes genéricas a endpoints con `requestEndpoint()`

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

    // Iniciar sesión de forma segura usando variables de entorno
    await client.login({
        accountID: process.env.GD_ACCOUNT_ID,
        password: process.env.GD_PASSWORD
    });

    // Leer mensajes recibidos
    const inbox = await client.readMessages();
    console.log(inbox);

    // Enviar un mensaje
    const sent = await client.sendMessage(
        29294657,
        "Hola Mundo",
        "¡Este es un mensaje de prueba!"
    );
    console.log(sent);

    // Obtener puntuaciones de un nivel
    const scores = await client.getLevelScores(1234567);
    console.log(scores);

    // Solicitud genérica a un endpoint
    const response = await client.requestEndpoint("uploadGJMessage20.php", {
        toAccountID: 29294657,
        subject: Buffer.from("Prueba").toString("base64"),
        body: Buffer.from("¡Hola!").toString("base64")
    });
    console.log(response);
})();
```

### Seguridad

* **NO incluyas tu contraseña de GD** en repositorios públicos.
* Usa variables de entorno (`.env`) u otro método seguro.
* `gjp2` se genera localmente; no se envía el login real, solo el hash.

### Referencias

* [GD Docs por Wyliemaster](https://wyliemaster.github.io/gddocs/)
* [Endpoints de la API de Geometry Dash](https://www.boomlings.com/database/)

### Licencia

MIT © SkunkPlatform
