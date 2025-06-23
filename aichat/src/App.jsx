import React, { useState } from "react";
import "./App.css";
import axios from "axios";

const App = () => {
  const [messages, setMessages] = useState([
    { type: "bot", text: "Hi! I'm JobBot. What kind of job are you looking for?" },
  ]);
  const [input, setInput] = useState("");

  


const sendMessage = async () => {
  if (!input.trim()) return;

  const userMessage = { type: "user", text: input };
  setMessages((prev) => [...prev, userMessage]);

  try {
    const response = await axios.post("http://localhost:8080/chat", {
      type: "user",
      text: input,
    });

    const botMessage = {
      type: "bot",
      text: response.data.text,
    };

    setMessages((prev) => [...prev, botMessage]);
  } catch (error) {
    setMessages((prev) => [
      ...prev,
      { type: "bot", text: "Error connecting to server." },
    ]);
  }

  setInput("");
};

  return (
    <div className="chat-container">
      <div className="chat-header">
        <img
          src="https://cdn-icons-png.flaticon.com/512/4712/4712100.png"
          alt="Bot Logo"
          className="bot-logo"
        />
        AI Job Searching Chatbot
      </div>
      <div className="chat-box">
        {messages.map((msg, i) => (
          <div key={i} className={`message-wrapper ${msg.type}`}>
            {msg.type === "bot" && (
              <img
                src="https://cdn-icons-png.flaticon.com/512/4712/4712100.png"
                alt="Bot"
                className="avatar"
              />
            )}
            <div className={`message ${msg.type}`}>{msg.text}</div>
            {msg.type === "user" && (
              <img
                src="https://cdn-icons-png.flaticon.com/512/4333/4333609.png"
                alt="User"
                className="avatar"
              />
            )}
          </div>
        ))}
      </div>
      <div className="chat-input">
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && sendMessage()}
          placeholder="Type your job preference..."
        />
        <button onClick={sendMessage}>Send</button>
      </div>
    </div>
  );
};

export default App; 
