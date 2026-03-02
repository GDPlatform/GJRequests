const { Client } = require("./index");

(async () => {
    const client = new Client();

    await client.login({
        accountID: 1234567890, // AccountID
        password: "insert your gd password" // Use your Geometry Dash Password on other account. ACTION REQUIRED: Environment Variables, DO NOT SHARE GD PASSWORD TO YOUR PUBLIC REPOSITORY THAT WILL POSSIBLE CAUSE RISKS. DO NOT ATTEMP TO SHARE.
    });

    const messages = await client.readMessages();
    console.log(messages);

    const submitted = await client.sendMessage(
        29294657, // Send to an Account ID
        "Subject", // this subject will be base64.
        "Body" // this body will be base64.
    );

    console.log(submitted);
})();
