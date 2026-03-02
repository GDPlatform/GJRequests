#pragma once
#include <string>
#include <map>
#include <stdexcept>
#include "credential.hpp"
#include "msg.hpp"
#include "auth.hpp"
#include "endpoint-request.hpp"

namespace GDClient {

    class Client {
    private:
        void requireLogin() const {
            if (accountID.empty() || gjp2.empty()) {
                throw std::runtime_error("Client not logged in. Call login() first.");
            }
        }

    public:
        std::string accountID;
        std::string gjp2;

        Client() : accountID(""), gjp2("") {}

        // Login: sets accountID and gjp2
        void login(const std::string& account, const std::string& password) {
            if (account.empty() || password.empty()) {
                throw std::runtime_error("AccountID and password are required.");
            }
            accountID = account;
            gjp2 = GDCredential::generateGjp2(password);
        }

        // Get level scores
        std::string getLevelScores(const std::string& levelID) {
            requireLogin();
            return GDAuth::getScores({{"accountID", accountID}, {"gjp2", gjp2}, {"levelID", levelID}});
        }

        // Send a private message
        std::string sendMessage(const std::string& toAccountID,
                                const std::string& subject = "untitled",
                                const std::string& body = "") {
            requireLogin();

            auto base64Encode = [](const std::string& input) -> std::string {
                return GDCredential::base64UrlEncode(input);
            };

            return GDMessage::upload(accountID, gjp2, toAccountID,
                                     base64Encode(subject),
                                     base64Encode(body));
        }

        // Read a single message
        std::string readMessage(const std::string& messageID) {
            requireLogin();
            return GDMessage::read(accountID, gjp2, messageID);
        }

        // Read inbox or sent messages
        std::string readMessages(int page = 0, bool sent = false) {
            requireLogin();
            return GDMessage::messages(accountID, gjp2, page, sent);
        }

        // Generic endpoint request
        std::string requestEndpoint(const std::string& endpoint,
                                    std::map<std::string, std::string> params = {},
                                    const std::string& secret = "Wmfd2893gb7") {
            requireLogin();

            // Ensure accountID, gjp2, secret are included
            params["accountID"] = accountID;
            params["gjp2"] = gjp2;
            params["secret"] = secret;

            return GDEndpoint::doPost("/database/" + endpoint, params);
        }
    };
}
