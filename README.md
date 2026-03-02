# GJRequests (for Java)

Geometry Dash API Client in **Java 7 / Java 10** – powered by [GDPlatform](https://github.com/GDPlatform).

---

## Features

* Login via **GJP2** (SHA1 of password + salt)
* Send private messages
* Read inbox and sent messages
* Retrieve level scores
* Generic endpoint requests
* Compatible with Java 7 and Java 10

---

## Project Structure

```
Java(7/8/10)/
│  Main.java                     # Main client class
├─ modules/
│  │  Auth.java                  # Level score helper
│  │  Credential.java            # GJP2, XOR, Base64 helper
│  │  Message.java               # Upload/read/fetch messages
│  │  EndpointRequest.java       # Generic POST request
```

---

## Installation

1. Clone the repository:

```bash
git clone https://github.com/GDPlatform/GJRequests.git
cd GJRequests
```

2. Compile Java modules:

* **Java 7**

```bash
javac -d bin Main.java modules/*.java
```

* **Java 10**

```bash
javac -source 10 -target 10 -d bin Main.java modules/*.java
```

---

## Usage Example

```java
import main.Main;

public class Test {
    public static void main(String[] args) {
        Main client = new Main();
        client.login("1234567890", "your_gd_password");

        // Get level scores
        String scores = client.getLevelScores("12345");
        System.out.println("Level scores: " + scores);

        // Send a message
        String sent = client.sendMessage("29294657", "Hello", "This is a test message!");
        System.out.println("Message sent: " + sent);

        // Read inbox messages
        String inbox = client.readMessages(0, false);
        System.out.println("Inbox messages: " + inbox);
    }
}
```

---

## Notes

* **Passwords are sensitive!** Never commit your GD password to a public repository.
* **Java 7 users:** Use `javax.xml.bind.DatatypeConverter` for Base64 encoding/decoding.
* **Java 10 users:** Use `java.util.Base64` (already in `Main.java`).
* Supports modern GD API (2.2+) with GJP2 authentication.