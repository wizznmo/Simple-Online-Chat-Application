# Online Chat Application

[![Java](https://img.shields.io/badge/Java-17-blue)](https://www.oracle.com/java/technologies/downloads/)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)]()
[![License](https://img.shields.io/badge/license-MIT-orange)](LICENSE)

A lightweight, text-based chat application built in Java using socket programming and multithreading.  
It demonstrates a client–server architecture capable of handling multiple users communicating in real time.

---

## Features

- Central server managing all client connections
- Multiple clients chatting concurrently
- Automatic message broadcasting
- Unique username assignment (User1, User2, etc.)
- Graceful handling of client disconnections

---

## Architecture Overview

```

```
      +-------------+
      | Chat Client |
      +-------------+
             |
             | TCP Connection
             v
      +-------------+
      | Chat Server |
      +-------------+
        /   |   \
       /    |    \
```

+-------+ +-------+ +-------+
|Client | |Client | |Client |
|Handler| |Handler| |Handler|
+-------+ +-------+ +-------+

````

---

## Getting Started

### Prerequisites
- Java Development Kit (JDK) installed  
- Terminal or command prompt access

### Compilation
```bash
javac *.java
````

### Run the Server

```bash
java ChatServer
```

### Run a Client

```bash
java ChatClient
```

---

## Usage

1. Start the server.
2. Launch one or more clients.
3. Type messages in any client window and press Enter.
4. Messages are instantly broadcast to all connected clients.
5. Type `exit` to leave the chat.

Example:

```
User1: Hello everyone!
User2: Hi User1, welcome!
```

---

## Project Structure

```
ChatServer.java     # Main server logic
ClientHandler.java  # Handles each client in a separate thread
ChatClient.java     # Client-side application
```

---
