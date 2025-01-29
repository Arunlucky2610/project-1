const secretKey = "mySecretKey123";

function encryptMessage(message) {
    return btoa(unescape(encodeURIComponent(message)));
}

function decryptMessage(encrypted) {
    return decodeURIComponent(escape(atob(encrypted)));
}

let messages = [
    { text: "Hello, Hedera!", timestamp: "2024-12-27 10:00:00" },
    { text: "Learning HCS", timestamp: "2024-12-27 10:01:00" },
    { text: "Message 3", timestamp: "2024-12-27 10:02:00" }
];

function sendMessage() {
    let input = document.getElementById("messageInput").value;
    let encryptedMessage = encryptMessage(input);
    messages.push({ text: encryptedMessage, timestamp: new Date().toISOString() });
    displayMessages();
}

function displayMessages(filter = "") {
    let messagesDiv = document.getElementById("messages");
    messagesDiv.innerHTML = "";
    messages.forEach(msg => {
        let decryptedText = decryptMessage(msg.text);
        if (filter === "" || decryptedText.includes(filter)) {
            let messageElement = document.createElement("div");
            messageElement.className = "message";
            messageElement.innerText = `${decryptedText} at ${msg.timestamp}`;
            messagesDiv.appendChild(messageElement);
        }
    });
}

function filterMessages() {
    let keyword = document.getElementById("filterInput").value;
    displayMessages(keyword);
}

window.onload = function() {
    displayMessages();
};
