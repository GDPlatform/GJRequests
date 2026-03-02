#pragma once
#include <string>
#include <map>
#include <stdexcept>
#include "endpoint-request.hpp" // doPost from previous header
#include "credential.hpp"       // optional if you want to encode/decode GJP

namespace GDMessage {

    const std::string SECRET = "Wmfd2893gb7";

    // Upload (send) a private message
    inline std::string upload(const std::string& accountID,
                              const std::string& gjp2,
                              const std::string& toAccountID,
                              const std::string& subject,
                              const std::string& body) {

        std::map<std::string, std::string> params = {
            {"secret", SECRET},
            {"accountID", accountID},
            {"gjp2", gjp2},
            {"toAccountID", toAccountID},
            {"subject", subject},
            {"body", body},
            {"gameVersion", "22"},
            {"binaryVersion", "42"},
            {"gdw", "0"}
        };

        return GDEndpoint::doPost("/database/uploadGJMessage20.php", params);
    }

    // Read (download) a private message
    inline std::string read(const std::string& accountID,
                            const std::string& gjp,
                            const std::string& messageID) {

        std::map<std::string, std::string> params = {
            {"secret", SECRET},
            {"accountID", accountID},
            {"gjp", gjp},
            {"messageID", messageID}
        };

        return GDEndpoint::doPost("/database/downloadGJMessage20.php", params);
    }

    // Get inbox or sent messages
    inline std::string messages(const std::string& accountID,
                                const std::string& gjp2,
                                int page = 0,
                                bool sent = false) {

        std::map<std::string, std::string> params = {
            {"secret", SECRET},
            {"accountID", accountID},
            {"gjp2", gjp2},
            {"page", std::to_string(page)},
            {"getSent", sent ? "1" : "0"}
        };

        return GDEndpoint::doPost("/database/getGJMessages20.php", params);
    }

}
