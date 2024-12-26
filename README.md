# Gwent-Like Java Card Game

This repository contains a Gwent-like card game implemented in Java. The game supports both online and offline play modes, providing an engaging experience with numerous features such as multiplayer gameplay, tournaments, and in-game chat.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Features

1. **Multiplayer Gameplay**
   - Play with friends using the online server.
   - Create private or public matches.

2. **Tournament Mode**
   - Organize and participate in tournaments with friends.
   - Track tournament standings in real-time.

3. **Offline Mode**
   - Enjoy a single-player experience without needing an internet connection.
   - Play against AI opponents with various difficulty levels.

4. **In-Game Chat**
   - Communicate with your opponent during online matches.

5. **Game Server**
   - Host a dedicated server to manage online gameplay.
   - Includes support for managing players, matchmaking, and chat functionality.

6. **Dynamic Card System**
   - Diverse set of cards with unique abilities.
   - Strategize to outwit your opponent using well-designed decks.

## Installation

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/MAQilH/Advanced-Programming-Project.git
   ```

2. Navigate to the project directory:
   ```bash
   cd Advanced-Programming-Project
   ```

3. Build the project using Maven:
   ```bash
   mvn clean install
   ```

4. Start the server:
   ```bash
   java -jar server.jar
   ```

5. Launch the game client:
   ```bash
   java -jar client.jar
   ```

## Usage

### Online Mode

1. Connect to the server by entering its address in the client.
2. Create or join a match to play with friends.
3. Use the in-game chat to strategize or talk to your opponent.

### Offline Mode

1. Launch the game in offline mode from the client.
2. Choose an AI difficulty level and start playing.

### Tournament Mode

1. Host or join a tournament through the server.
2. Compete against other players in a structured format.

## Project Structure

```plaintext
Advanced-Programming-Project/
├── server/
│   ├── src/
│   └── resources/
├── client/
│   ├── src/
│   └── resources/
├── shared/
│   ├── models/
│   └── utilities/
├── README.md
├── pom.xml
└── LICENSE
```

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Commit your changes with clear and descriptive messages.
4. Push your changes to your fork.
5. Open a pull request to the main repository.

## License

This project is licensed under the [MIT License](./LICENSE).

---

Thank you for exploring the Gwent-like Java Card Game! Your feedback and suggestions are always appreciated.

