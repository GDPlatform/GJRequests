#pragma once
#include <string>
#include <curl/curl.h>
#include <sstream>
#include <map>
#include <stdexcept>

namespace GDEndpoint {

    const std::string GD_SERVER = "https://www.boomlings.com";

    // Helper for curl response
    static size_t WriteCallback(void* contents, size_t size, size_t nmemb, void* userp) {
        ((std::string*)userp)->append((char*)contents, size * nmemb);
        return size * nmemb;
    }

    // Convert key-value map to URL-encoded query string
    inline std::string urlencode(const std::map<std::string, std::string>& params) {
        std::ostringstream oss;
        for (auto it = params.begin(); it != params.end(); ++it) {
            if (it != params.begin()) oss << "&";
            char* encodedKey = curl_easy_escape(nullptr, it->first.c_str(), 0);
            char* encodedVal = curl_easy_escape(nullptr, it->second.c_str(), 0);
            oss << encodedKey << "=" << encodedVal;
            curl_free(encodedKey);
            curl_free(encodedVal);
        }
        return oss.str();
    }

    // Generic POST request helper
    inline std::string doPost(const std::string& path, const std::map<std::string, std::string>& params,
                              const std::string& userAgent = "GeometryDash/2.11") {
        CURL* curl = curl_easy_init();
        if (!curl) throw std::runtime_error("Failed to initialize CURL");

        std::string url = GD_SERVER + path;
        std::string postData = urlencode(params);
        std::string response;

        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_POSTFIELDS, postData.c_str());
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &response);
        curl_easy_setopt(curl, CURLOPT_USERAGENT, userAgent.c_str());
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 1L);

        CURLcode res = curl_easy_perform(curl);
        if (res != CURLE_OK) {
            curl_easy_cleanup(curl);
            throw std::runtime_error(std::string("CURL error: ") + curl_easy_strerror(res));
        }

        curl_easy_cleanup(curl);
        return response;
    }

}
