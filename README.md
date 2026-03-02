# GJRequests C++ SDK

**[cpp branch] GDPlatform/GJRequests**

A C++ SDK for interacting with **Geometry Dash** APIs, fully inspired by the Node.js `gjrequest.js`.
Provides **login, messaging, level scores**, and generic endpoint requests.

---

## Features

* Generate `GJP2` hash for authentication (`+2.2` accounts).
* Send and read private messages.
* Read inbox or sent messages.
* Get level scores.
* Generic requests to any Geometry Dash endpoint.
* Base64 URL-safe encoding/decoding integrated.

---

## Installation

1. Clone the repository:

```bash
git clone -b cpp https://github.com/GDPlatform/GJRequests.git
cd GJRequests
```

2. Install dependencies:

* **libcurl** (for HTTPS requests)
* **OpenSSL** (for SHA1 hash)

*On Windows, we recommend using [vcpkg](https://vcpkg.io/)*:

```powershell
.\vcpkg install curl:x64-windows
.\vcpkg install openssl:x64-windows
```

3. Build using CMake:

```bash
mkdir build && cd build
cmake ..
cmake --build . --config Release
```

---

## Usage

```cpp
#include <iostream>
#include "main.hpp"  // GDClient::Client

int main() {
    GDClient::Client client;

    // Login
    client.login("1234567890", "your_gd_password");

    // Get inbox messages
    std::string inbox = client.readMessages();
    std::cout << "Inbox:\n" << inbox << std::endl;

    // Send a message
    std::string result = client.sendMessage("29294657", "Hello", "This is a test message!");
    std::cout << "Send result:\n" << result << std::endl;

    // Get level scores
    std::string scores = client.getLevelScores("12345");
    std::cout << "Level scores:\n" << scores << std::endl;

    return 0;
}
```

---

## Notes

* **Security warning**: Never commit your GD password to public repositories. Always use environment variables when possible.
* The SDK is fully C++17 compatible.
* All requests are **HTTPS** via `libcurl`.
* Base64 encoding/decoding is URL-safe to match Geometry Dash expectations.

---

## Modules

| Header                 | Purpose                               |
| ---------------------- | ------------------------------------- |
| `credential.hpp`       | GJP2 generation, Base64 encode/decode |
| `msg.hpp`              | Upload/read messages, inbox/sent list |
| `auth.hpp`             | Get level scores                      |
| `endpoint-request.hpp` | Generic POST helper for GD endpoints  |
| `main.hpp`             | Client class integrating all modules  |

---

This README reflects the **C++ branch** (`cpp`) of **GJRequests**, designed for developers who want to interact with Geometry Dash APIs natively in C++.