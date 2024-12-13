# Sky Strike Saga

## GitHub Repository
[Link to Repository](<https://github.com/hooriya067/DevMainSoft-CW2024>)

---

## Compilation Instructions
---
1. Ensure you have Java Development Kit (JDK 17 or higher) installed.
2. Use IntelliJ IDEA (Community Edition) .
3. Clone the repository using:
   ```bash
   git clone <https://github.com/hooriya067/DevMainSoft-CW2024.git>
   
   ```
   Open Project in IntelliJ via File > Open....
4. *Load Maven Dependencies*: IntelliJ auto-loads dependencies. If not, click the Maven tool window and refresh.
5. *Build Project*: Select Build > Build Project.
6. *Run Application*: Click the green Run button or press Shift + F10.
7. *Play the Game*: The game window will open. Enjoy!
8. Ensure dependencies such as JavaFX are properly configured in your environment.
   *Dependencies*: Maven for dependencies, JavaFX for UI.
---

## Implemented and Working Properly

### *Gameplay Features*
---
### <u>Level Progression</u>

**Level 1: Introduction**
- Introduces the basic gameplay mechanics for players.
- Features standard enemy planes with straightforward movement patterns.
- Players familiarize themselves with controls, firing mechanics, and navigating the game environment.

**Level 2: The Boss Encounter**
- Introduces a boss enemy with a shield and unpredictable movement, creating the first significant challenge.
- Players must deplete the boss's health while avoiding its projectiles.
- Shields are visually represented and can regenerate, demanding strategic attacks.

**Level 3: Enhanced Gameplay with Formations**
- Adds a new layer of complexity with V-shaped enemy formations led by a tougher leader enemy, destroying the leader scatters the whole enemy formation, forcing players to adapt their strategy.
- Introduces slower-moving planes with higher life that shoot homing missiles.Homing Missiles are deadly projectiles that follow the user movement.
- Improves player maneuverability with left-right movement, allowing greater control.

**Level 4: Stealth and Hazard Mechanics**
- Introduces stealth enemies that remain hidden until they enter the player’s radius. Their health is high, and they inflict greater damage upon collision.
- Bomb projectiles fall from random positions at the top of the screen, adding navigation challenges.
- "Flare" power-ups provide a tactical advantage by revealing stealth enemies temporarily.
- Gameplay revolves around collecting power-ups to safeguard against bomb collisions and eliminate stealth enemies.
- Dedicated collision handling ensures smooth integration of these new mechanics.

**Level 5: The Climactic Showdown**
- Starts with a dramatic "SHOWDOWN!!" text, creating a cinematic transition into the level.
- A final battle requires players to navigate through enemies from all previous levels during rain and meteor shower.
- While the "SHOWDOWN!!" text and kill counter are functional, the meteor storm mechanic and rain effects require debugging for consistent diagonal movement.
---
### <u>Dynamic User Tracking System</u>
- **Overview:**
    - The system ensures real-time monitoring and updates of key player resources like coins and bullets throughout the game, providing seamless integration with gameplay elements.

- **Features:**
    - **Coin Tracking:**
        - Managed by the `CoinSystemManager`, coins increment dynamically when collected and persist across levels.
        - UI elements reflect real-time updates, ensuring players are always aware of their current balance.
    - **Bullet Tracking:**
        - Handled by the `BulletSystemManager`, tracking bullets fired and remaining.
        - Players can purchase additional bullets, with the UI dynamically displaying updates.

- **Gameplay Impact:**
    - Promotes strategic decision-making by balancing resource usage and collection.
    - Provides immediate feedback via real-time updates, enhancing immersion and player engagement.
### <u>Coin System</u>
Managed by the `CoinSystemManage` class
The in-game currency mechanism allows players to collect and use coins strategically during gameplay:

- **Persistence Across Levels:** Coins collected in one level carry over to subsequent levels using a singleton `CoinSystemManager` class.
- **Dynamic Updates:** The coin count dynamically updates in the UI whenever coins are collected or spent.
- **Integration with Power-Ups:** Coins are spent on purchasing power-ups that provide critical gameplay advantages. Power-ups include extra lives, shields, and additional bullets, offering players strategic choices to enhance their survivability and effectiveness.
- **Integration with Customisation System:** Coins are also used to buy Customised planes with higher health and higher speeds, giving user an advantage over the enemies.

**Gameplay Impact:**
The Coin System encourages players to balance offensive actions with coin collection, rewarding those who prioritize gathering resources for future challenges.
---
### <u>Power-Up Management</u>

Managed by the `PowerUpManager` class, the system allows players to spend their collected coins on valuable upgrades during gameplay that can help them and get them advantage over enemies.

**Available Power-Ups:**

1. **Extra Life:**
    - **Cost:** A fixed number of coins.
    - **Effect:** Grants an additional heart to the player’s health bar, improving survivability during challenging encounters.
    - **Integration:** Purchased via the Power-Up Menu.

2. **User Shield:**
    - **Cost:** A fixed number of coins.
    - **Effect:** Temporarily makes the player's plane invulnerable, absorbing all incoming damage for a limited duration of 20 seconds.
    - **Integration:**
        - Activates immediately upon purchase and provides visual feedback to indicate invulnerability.
        - Automatically deactivates after the set duration, reintroducing vulnerability.

3. **Extra Bullets:**
    - **Cost:** A fixed number of coins.
    - **Effect:** Restocks the player's bullet count, ensuring they can continue firing in prolonged battles or resource-intensive levels.
    - **Integration:** Dynamic bullet tracking in the UI ensures players are aware of their remaining ammunition.

**Gameplay Impact:**
The Power-Up System enhances player strategy by offering upgrades tailored to their immediate needs. Whether increasing health for endurance, activating a shield for safety, or replenishing bullets for offense, these power-ups allow players to adapt their approach to the game's challenges effectively. 
---
### <u>Customization System</u>
Managed by  `CustomizationScreen` class.
The Customization System provides players with the opportunity to personalize their gameplay experience by selecting unique planes with varying attributes. This system integrates seamlessly with the game's mechanics, encouraging players to earn and spend coins strategically.
1.<u> **Plane Selection:**</u>
    - Players can choose from multiple plane designs displayed in a visually appealing gallery.
    - Each plane has unique attributes:<br><br>
        - **1) Default Plane:** Free and balanced, ideal for starting gameplay.
        - <br>**2) Advanced Planes:** Cost coins but provide significant advantages in speed, health, and maneuverability.

2. <u>**Pricing and Coin Integration:**</u>
    - Each plane has a specific price:
        - **Default Plane:** Free.
        - **Plane 2:** 10 coins.
        - **Plane 3:** 15 coins.
    - Coins collected during gameplay are spent on unlocking advanced planes.
    - The `CoinSystemManager` dynamically updates the coin count in real-time, ensuring players can track their purchasing power.

3. <u>**Customization Process:**</u>
    - Planes are displayed with prices and attributes such as health, speed, and size.
    - Players click on a plane to select it, with the selected plane glowing to confirm the choice.
    - A **CONFIRM** button finalizes the selection, deducting coins and applying the customization.

4. **Performance and Attributes:**
    - Each plane offers tailored gameplay benefits:
        - **Plane 1:** Balanced attributes for standard gameplay.
        - **Plane 2:** Higher speed and moderate health, ideal for quick maneuvering.
        - **Plane 3:** Maximum durability and speed for challenging levels.

5. **Integration with Other Systems:**
    - The Customization System interacts with the `UserPlaneFactory` to update the player's selected plane dynamically.
    - Purchased planes persist across levels, ensuring the player's choice is impactful throughout the game.
    - The Customization System also takes use of `AlertManager` to alert the player if enough coins are not present.
**Gameplay Impact:**
The Customization System incentivizes players to collect coins and make strategic decisions about plane selection. By offering tangible gameplay benefits like enhanced speed and health, it enriches the player's experience and adds depth to the game's progression system.
---
### <u>Star System</u>

Evaluates player performance in each level by rewarding stars based on efficiency, particularly focusing on bullet usage. It serves as a measure of skill and provides an incentive for players to optimize their gameplay.

1. **Performance Tracking:**
    - Tracks the number of bullets used by the player in each level through the `BulletSystemManager`.
    - Calculates stars based on the player’s bullet usage compared to an optimal threshold. Efficient gameplay earns more stars.

2. **Star Calculation:**
    - **Level Stars:**
        - Stars are calculated at the end of each level.
        - Levels have predefined criteria for achieving one to five stars, rewarding players who complete levels with fewer bullets.
    - **Final Stars:**
        - At the end of the game, stars earned in all levels aggregate, applying a slight penalty for inefficiencies.

3. **Integration:**
    - The `StarDisplay` class visually represents stars during level completion and the game’s win screen.
    - Features animated stars and a label displaying the number of stars earned for each level.
    - Final stars are prominently displayed on the game completion screen, giving players a sense of accomplishment.
    - After every Play again option in Win Page and Gameover, Bullet count is set back to The original bullet count as new game starts. Howverin level complete screen, same bullet count is maintained and passed on as user doesnt reset the whole game but just voluntarily wants to play same level again.
**Gameplay Impact:**
The Star System encourages players to adopt a more strategic approach to gameplay, minimizing bullet usage while maximizing efficiency. It serves as a reward mechanism, motivating players to replay levels to achieve higher star ratings and perfect their performance.

---

## *UI and Interactivity*

### <u>Main Menu</u>

- **Functionality:**
    - The Main Menu serves as the entry point to the game, offering options to:
        - Start a new game.
        - Quit the application.
        - Customize user’s plane.
        - Instruction Manual telling user about the game.
        - Toggle sound button.

- **Visual Enhancements:**
    - Features dynamic button styling, including hover effects and animations, to create an interactive and visually appealing experience.
    - Includes a title banner with stylized text, setting the tone for the game.

---

### <u>Pause and Power-Up Menus</u>

1. **Pause Menu:**
    - **Functionality:**
        - Allows players to pause gameplay with options to resume, quit, or return to the main menu.
        - Accessible via a pause button.
        - When user resumes game, the game starts with a 3,2,1 timer giving user time to buckle up.

    - **Design:**
        - Overlays the gameplay screen with a semi-transparent background to maintain context.
        - Features large, clearly labelled buttons contained in a box for ease of navigation during intense moments.
<br><br>
2. **Power-Up Menu:**
    - **Functionality:**
        - Allows players to spend coins on valuable power-ups like extra lives, bullets and shields.
        - Features clickable buttons for each power-up, dynamically updating based on the player's coin count.
        - If a user tries to buy a powerup that they don’t have enough coins for, an alert message shows saying ‘not enough coins for that specific powerup’. User can use back option to resume the game.
        - If a user buys a powerup, game automatically resumes and a message show up to confirm that power up has successfully been bought.

    - **Design:**
        - Includes icons for each power-up to explain their effects.
        - Displays the price of powerups and coin balance to assist in decision-making.

---

### <u>Instruction Screen</u>

- **Functionality:**
    - Provides new players with a step-by-step guide to the game mechanics, controls, and objectives.
    - Includes a slide show of game screens with instructions, navigation arrows to move through slides, and a close button to exit the screen at any time.

- **Design:**
    - A blurred background ensures the instructions stand out without entirely disconnecting the player from the game’s context.
    - Features clear, concise text paired with visuals (e.g., icons and images) to enhance understanding.

---

### <u>Level Introduction Screen</u>

- **Functionality:**
    - Displays each level’s objectives and specific gameplay mechanics before the level begins.
    - Informs players of unique challenges and win conditions for the level.
    - To continue, player can press enter key and kickstart the level.

- **Design:**
    - Incorporates a glowing bubble effect for highlighting objectives.
    - Typing animation simulates dynamic text entry, drawing the player’s attention to critical details.

---

### <u>Customization Screen</u>

- **Functionality:**
    - Allows players to choose their plane, unlocking new designs and new types of planes.

- **Visual Design:**
    - Includes a gallery-style layout for planes with preview animations, enabling players to see their selection in action with their prices.
    - When a player chooses a plane, the plane glows bright showcasing that it has been chosen. By clicking the CONFIRM Button player can proceed with their customised plane, and user is taken back to main menu page.

---

### <u> Level Completed Screen </u>

- **Functionality:**
    - Displays a congratulatory overlay upon completing a level.
    - Highlights the player's achievements for the level, such as:
        - Stars earned based on performance.
    - Includes navigation options to proceed to the next level or return to the main menu.

- **Design:**
    - Features animated star icons to showcase the earned stars dynamically.
    - Displays an eye-catching Level Complete Banner.
    - Interactive buttons:
        - **NEXT LEVEL:** Proceeds to the next level in the game.
        - **PLAY AGAIN:** Returns to the same level player was playing, allowing players to play the level again if they want to without taking them all the way to the first level.

---

### <u>Win Page</u>

- **Functionality:**
    - Appears after the player successfully completes the final level of the game.
    - Celebrates the player’s victory with dramatic sound effects.
    - Displays cumulative stats:
        - Total stars earned throughout the game.
    - Offers replayability with options to restart the game or quit the game.

- **Design:**
    - Features a vibrant "You Win” banner with a ‘Winner Winner Chicken Dinner’ Victory motto.
    - Includes buttons:
        - **PLAY AGAIN:** Restarts the game from Level One.
        - **QUIT:** To quit the game if user wants to.

---

### <u> Game Over Page</u>

- **Functionality:**
    - Displays after the player loses all lives and fails to meet the level objectives.
    - Provides options for players to restart or quit the game.

- **Design:**
    - Includes a prominent "YOU LOSE" message with a voiceover tone.
    - Features interactive buttons:
        - **PLAY AGAIN:** Restarts the current level, allowing players to attempt it again.
        - **QUIT:** To quit the game if user wants to.
    - Visual cues, such as a dimmed background reinforce the game-over theme.

---

## *Audio and Visual Features*

### <u>Dynamic Music</u>

- **Background Music:**
    - Each level features background music that complements game theme, enhancing the immersive experience.
    - Music dynamically adjusts based on gameplay context:
        - Fades during menus to maintain focus on the interface.
    - Volume adjustments are smoothly managed through the `SoundManager` class, ensuring seamless transitions.

- **Sound Effects:**
    - High-quality sound effects for various in-game actions:
        - **Firing:** Distinct sounds for player and enemy projectiles.
        - **Coin Collection:** A rewarding chime that accompanies the "+1" animation.
        - **Winning Levels:** A celebratory tune that plays during level completion and the final victory screen.
        - **Game Over screen:** A Game Over voiceover when user loses.
---
## *Level Aesthetics*

### <u>Interactive Backgrounds </u>
- Background in later levels feature subtle animations like moving clouds.

### <u> Alerts and Animations</u>

- **Informational Alerts:**
    - In-game alerts provide real-time information about new mechanics or gameplay changes:
        - Examples include "LEFT-RIGHT MOVEMENT ENABLED" or "SHIELD ACTIVATED”.
        - Alerts are displayed prominently but fade out after a few seconds to avoid obstructing gameplay.
    - Managed through the `Alert Manager` class for consistency across levels.

- **Dynamic Animations:**
    - **Coin Collection:**
        - A "+1" animation appears whenever a coin is collected, accompanied by sound and visual effects.
        - Coins disappear and animation fades, creating a rewarding visual loop.
    - **Power-Up Usage:**
        - Shields activate with an actual shield on the top of players plane, that follows the player’s movement and shield’s player from any damage. It is accompanied by a timer of 20 seconds.
        - Extra lives visually add hearts to the health bar, with a smooth scaling animation.
  ### <u>Collision Visualization</u>

#### **Impact Animation**
- **Explosion Effect**:
    - On any plane collision, a visually striking explosion image appears at the collision point.
    - The image is displayed for 1 second and gradually fades out, adding a dynamic feedback element for the player.

#### **Technical Implementation**
- **Collision Detection**:
    - The `CollisionManager` detects collisions between planes and triggers the explosion animation.

- **Dynamic Placement**:
    - The explosion image is dynamically added to the scene at the exact point of intersection between the two colliding objects.

- **Timed Animation**:
    - A fade-out effect is applied to the explosion image, ensuring it smoothly disappears after 1 second, blending seamlessly into the gameplay.
---
## *Features Implemented but Not Working Properly*
---
### <u>Meteor Behaviour in Level Five</u>

- **Current Status:** Meteors were introduced as a dynamic obstacle in Level Five. They are designed to spawn from the top-right corner of the screen and travel diagonally across the battlefield.
- **Issue:** The meteors do not consistently follow the intended diagonal trajectory. Although according to debugging, they are being created properly. Instead, their movement appears erratic at times, always moving out of screen, which detracts from the gameplay experience.
- **Possible Causes:**
    - Improper velocity calculation or direction assignment during meteor initialization.
    - Collision detection or boundary handling interfering with smooth movement.

---

### <u>Rain Effect in Level Five</u>

- **Implementation:** A rain effect was planned to enhance the visual aesthetic and create a dynamic environment. It included falling raindrops animated to descend from random positions on the screen.
- **Current Status:** The code for spawning and animating raindrops exists but is commented out due to rendering issues. Specifically, raindrops are not visually appearing on the screen despite being logged as active.
- **Challenges Faced:**
    - **Visibility:** Raindrops may not be rendering correctly within the game’s scene graph.
    - **Layering:** The rain container might be positioned incorrectly, rendering raindrops behind other elements.

---
### <u>Heart Display for Customised Planes</u>

- **Implementation:**The customisation and coin system work perfectly,completely in sync with eachother. The additional plane faetures also get added perfectly.
- **Current Status:**Everything works like it should but extra hearts from advanced playersa are overlapping with UI elements like kill count a bit, causing a bit of UI overlapping, but kill no. still seen perfectly.


---
## *Features Not Implemented*

### <u>Online Profiles</u>

- **Description:**
    - Online profiles were planned to allow players to log in and save their progress, such as stars, coins, unlocked planes, and other customizations, across sessions and devices.
    - This feature would have supported a personalized and persistent gameplay experience.
- **Reason for Omission:**
    - **Technical Complexity:** Implementing a login system and saving data securely requires a backend or database integration, which was outside the current scope.
    - **Dependency on Other Features:** The absence of online profiles directly impacted features like customization options for planes, as tracking unlocked planes would have required persistent storage.
    - **Time and Resource Constraints:** Developing a secure, fully functional system for user profiles would have significantly delayed the project timeline.

---

### <u>Stealth Enemy Enhancements</u>

- **Description:**
    - Stealth enemies were introduced in Level Four, but their functionality was kept basic. Planned enhancements included dynamic AI behaviour, such as adaptive movement, more intelligent hiding mechanisms, and reactive attacks based on the player’s strategy.
- **Reason for Omission:**
    - **Advanced AI Requirements:** Developing stealth AI with these features required complex algorithms and extensive testing to maintain balance.
    - **Focus on Core Features:** Limited time and resources were allocated to finalize core gameplay elements like coin system, power-ups, level transitions, and the star system, which took priority over advanced enemy AI.
    - **Bug Risks:** Other complex features with stealth mechanics, such as visibility toggling and collision handling, required attention, leaving insufficient bandwidth for further enhancements.

---


---
## *New Java Classes*

---
### <u>Level-Related</u>

- **LevelManager:**  
    - **Location:** `com.example.demo.Managers.LevelManager`  
    - **Description:** Manages the flow of the game by handling level progression, loading necessary assets, and ensuring smooth transitions between levels. Encapsulates logic for moving between levels, loading Level Introduction Pages, Level Complete pages, and Win Pages.

- **LevelIntroScreen:**  
    - **Location:** `com.example.demo.UI.screens.LevelIntroScreen`  
   -  **Description:** Displays level-specific objectives and instructions to the player before each level begins. Uses animations and dynamic content to enhance user experience and engagement, providing a clear understanding of the gameplay goals.

- **LevelFive, LevelFour, LevelThree:**  
    - **Location:** `com.example.demo.Levels`  
   -  **Description:** These classes define unique gameplay mechanics, specific objectives, and tailored enemy behaviors for each level. They include methods for spawning enemies, handling level progression, and implementing distinct challenges to provide varied gameplay experiences.

- **LevelViewLevelFive, LevelViewLevelFour, LevelViewLevelThree:**  
    - **Location:** `com.example.demo.Levels.view`  
    - **Description:** Responsible for defining the visual layout and user interface of their respective levels. Handle the positioning of UI components and overlays, ensuring the player's experience matches the unique theme of each level.

- **LevelCompletedScreen:**  
    - **Location:** `com.example.demo.UI.screens.LevelCompletedScreen`  
   -  **Description:** Displays a congratulatory message and relevant statistics after successfully completing a level. Includes options for the player to proceed to the next level or replay the same level, providing seamless transition.

---

### <u>Factories</u>

- **ProjectileFactory:**  
    - **Location:** `com.example.demo.actors.active.Factories.ProjectileFactory`  
    - **Description:** Implements a standardized and centralized way to create various types of projectiles. Simplifies the instantiation of user and enemy projectiles while ensuring consistency in behavior and attributes across the game.

- **EnemyFactory:**  
   -  **Location:** `com.example.demo.actors.active.Factories.EnemyFactory`  
   -  **Description:** Dynamically generates different types of enemy units using the factory pattern. Allows the game to spawn enemies with diverse characteristics and behaviors efficiently, enabling easier addition of new enemy types without altering existing code.

- **UserPlaneFactory:**  
  - **Location:** `com.example.demo.actors.active.Factories.UserPlaneFactory`  
   -  **Description:** Handles the dynamic creation of user-controlled planes, allowing for customization based on user preferences. Ensures user planes are initialized with the correct attributes and configurations for consistent gameplay.
### <u>Buttons</u>

- **ButtonParent:**  
   -  **Location:** `com.example.demo.UI.buttons.ButtonsParent`  
   -   **Description:** Simplifies button creation with centralized styling and behavior.

- **MainMenuButton:**  
   -  **Location:** `com.example.demo.UI.buttons`  
   -  **Description:** Takes the user to the main menu screen from the pause screen.

- **PauseMenu:**  
   -  **Location:** `com.example.demo.UI.buttons`  
   -  **Description:** Provides a visually attractive pause overlay with resume, quit, and main menu options.

- **PlayAgainButton:**  
    - **Location:** `com.example.demo.UI.buttons`  
    - **Description:** Restarts the game differently based on location. Takes the user back to Level One from the Game Over and Win page. Takes the user back to the previous level from the Level Complete page.

- **PowerUpButton:**  
    - **Location:** `com.example.demo.UI.buttons`  
    - **Description:** Opens the Power-Up Menu for purchasing upgrades and power-ups.

- **QuitButton:**  
    - **Location:** `com.example.demo.UI.buttons`  
    - **Description:** Exits the game and allows the user to leave at any time.

- **ResumeButton:**  
    - **Location:** `com.example.demo.UI.buttons`  
  - **Description:** Resumes gameplay after pausing. The gameplay resumes with a 3, 2, 1 countdown to give the user time to prepare.

- **SoundButton:**  
    - **Location:** `com.example.demo.UI.buttons`  
   -  **Description:** Adjusts audio settings, allowing the user to toggle background music on/off.

- **StartGameButton:**  
    - **Location:** `com.example.demo.UI.buttons`  
   -  **Description:** Initiates gameplay from the main menu and starts the first level.

- **NextButton:**  
    - **Location:** `com.example.demo.UI.buttons`  
   -  **Description:** Navigates to the level introduction of the next upcoming level.

- **CustomizeButton:**  
   -  **Location:** `com.example.demo.UI.buttons`  
   -  **Description:** Opens the Customization Screen for modifying the user plane.

- **InstructionButton:**  
   -  **Location:** `com.example.demo.UI.buttons`  
    - **Description:** Opens the instruction manual for the user to learn about the gameplay.
### <u>Game Mechanics</u>

---

- **StageManager:**
    - **Location:** `com.example.demo.core.StageManager`
    - **Description:** Manages transitions between game stages and keeps a singleton stage. The stage can be retrieved at any time and in any class without changing constructors.

- **GameConfig:**
    - **Location:** `com.example.demo.core.GameConfig`
    - **Description:** Centralized configuration for global game settings like screen size and key bindings. Adheres to the Single Responsibility Principle.

- **GameLoop:**
    - **Location:** `com.example.demo.core.GameLoop`
    - **Description:** Handles the game timeline and the updatable components of the game separately. Adheres to the Single Responsibility Principle.

- **GameStateManager:**
    - **Location:** `com.example.demo.core.GameStateManager`
    - **Description:** Manages game states like paused and resumed states, and handles game-over logic. Uses a flag to switch between paused and resumed states.

- **CollisionManager:**
    - **Location:** `com.example.demo.Managers.CollisionManager`
    - **Description:** Handles collision detection and response. Encapsulates collision-related logic, improving modularity.Independently handling collisons with the help of cator manager and using updatable to update them.

- **ActorManager:**
    - **Location:** `com.example.demo.Managers.ActorManager`
    - **Description:** Centralized management of all actors (friendly units, enemy units, projectiles, coins). Adheres to the Single Responsibility Principle.

- **InputHandlingManager:**
    - **Location:** `com.example.demo.Managers.InputHandlingManager`
    - **Description:** Manages player input, such as movement and firing. Centralized input handling ensures maintainability.

- **Updatable Interface:**
    - **Location:** `com.example.demo.core.Updatable`
    - **Description:** Standardizes update logic for updatable components across the game.Makes Level Parent free of all these logics.

- **MyObserver:**
    - **Location:** `com.example.demo.MyObserver`
    - **Description:** Custom observer interface for game state updates.

- **Enemy Interface:**
    - **Location:** `com.example.demo.actors.active.enemies.Enemy`
    - **Description:** Provides a base structure and methods for all enemy planes.

- **ControllableLevel Interface:**
    - **Location:** `com.example.demo.Levels.ControllableLevel`
    - **Description:** Facilitates flexible level management and interactions, adhering to encapsulation principles.

---

### <u>Gameplay-Related</u>


#### **Projectiles**

- **BombProjectile:**
    - **Location:** `com.example.demo.actors.active.projectiles`
    - **Description:** Introduced in Level Four, this projectile moves downward and causes significant area damage. It adds an extra layer of challenge for the player by requiring precise avoidance.

- **EnemyProjectileLevelThree:**
    - **Location:** `com.example.demo.actors.active.projectiles`
    - **Description:** Specialized for Level Three, these projectiles are fired by advanced enemy planes and feature increased speed or modified trajectories to challenge players further.

- **HomingMissile:**
    - **Location:** `com.example.demo.actors.active.projectiles`
    - **Description:** A dynamic projectile that tracks the player’s movement in real time, adding tension and requiring skillful evasion.

- **Meteor:**
    - **Location:** `com.example.demo.actors.active.projectiles`
    - **Description:** Appears during Level Five's meteor storm phase. These environmental hazards introduce randomness and unpredictability to the gameplay, forcing players to adapt.(Supposed to work but it isnt)

---

#### **Enemy System**

- **EnemyParent:**
    - **Location:** `com.example.demo.actors.active.enemies`
    - **Description:** Serves as an abstract base class for all enemy types, encapsulating shared behaviors such as health management, movement logic, and projectile firing.

- **EnemyPlaneTypeA & EnemyPlaneTypeB:**
    - **Location:** `com.example.demo.actors.active.enemies`
    - **Description:**
        - **Type A:** Features sinusoidal movement, making them more challenging to hit.
        - **Type B:** Fires homing missiles, introducing an advanced threat to the gameplay.This plane is slower moving but with higher life

- **StealthEnemyPlane:**
    - **Location:** `com.example.demo.actors.active.enemies`
    - **Description:** Boasts invisibility mechanics that require the player to use power-ups like FlarePowerUp to detect and destroy them. This adds a strategic element to the gameplay.

---

#### **Coin System**

- **Coin:**
    - **Location:** `com.example.demo.actors.collectibles.Coin`
    - **Description:** When collected, increases the player's coin count, allowing them to purchase power-ups or additional bullets.

- **CoinSystemManager:**
    - **Location:** `com.example.demo.Managers.CoinSystemManager`
    - **Description:** Handles the global coin count, ensuring consistency across levels and UI updates.

---

#### **Power-Ups**

- **FlarePowerUp:**
    - **Location:** `com.example.demo.actors.collectibles.FlarePowerUp`
    - **Description:** Temporarily reveals stealth enemies, allowing players to target and destroy them. Its strategic use is critical in levels featuring invisible threats.

- **PowerUpManager:**
    - **Location:** `com.example.demo.Managers.PowerUpManager`
    - **Description:** Facilitates the logic for purchasing and activating power-ups. Works seamlessly with the coin system and enhances the player's strategic choices during gameplay.

---

#### **Star System**

- **BulletSystemManager:**
    - **Location:** `com.example.demo.Managers.BulletSystemManager`
    - **Description:** Tracks and manages the player’s bullet count, ensuring that firing and purchasing mechanisms are consistent. It also provides feedback on bullet usage for determining stars earned.

- **StarManager:**
    - **Location:** `com.example.demo.Managers.StarManager`
    - **Description:** Manages the calculation and tracking of stars earned during gameplay based on performance metrics like bullet usage and objectives achieved.Calculates stars at the end og each level and at winpage.

- **StarDisplay:**
    - **Location:** `com.example.demo.actors.active.collectibles.StarDisplay`
    - **Description:** Provides a visual representation of the stars earned, reinforcing the player’s accomplishments and progression.

---
### <u>Auxiliary Components</u>

---

#### **Effects and Utilities**

- **Formation:**
    - **Location:** `com.example.demo.actors.active.Formation`
    - **Description:** Manages complex enemy formations, ensuring coordinated movements and behavior patterns among grouped enemy units.

- **AlertManager:**
    - **Location:** `com.example.demo.Managers.AlertManager`
    - **Description:** Dynamically displays informational and warning alerts during gameplay, improving user feedback.

- **SoundManager:**
    - **Location:** `com.example.demo.Managers.SoundManager`
    - **Description:** Handles game audio, including background music and sound effects. Provides dynamic controls for managing sound states.

- **Cloud:**
    - **Location:** `com.example.demo.Levels.effects.Cloud`
    - **Description:** Adds cloud effects to levels, particularly Level Five, to enhance visual appeal. Features smooth directional movement with customizable boundaries.

- **RainEffect:**
    - **Location:** `com.example.demo.Levels.effects.RainEffect`
    - **Description:** Animates rain in the background for aesthetic enhancement. This feature is currently in development and not fully functional.

---

#### **UI Screens**

- **MainMenu:**
    - **Location:** `com.example.demo.UI.screens.MainMenu`
    - **Description:** Serves as the primary entry point, allowing players to start the game, customize options, or exit the application.

- **CustomizationScreen:**
    - **Location:** `com.example.demo.UI.screens.CustomizationScreen`
    - **Description:** Allows players to customize planes, offering options for visuals and initial attributes like health.Checks the user's coins availibility and manages customisation accordingly.Has a confirm button for user to confirm the plane and move forward.

- **InstructionScreen:**
    - **Location:** `com.example.demo.UI.screens.InstructionScreen`
    - **Description:** Provides comprehensive gameplay instructions, detailing controls and mechanics.It takes user to a slideshow of instructions which are visually appealing, for user to understand game mechanics and rules.

---

#### **In-Game Menus**

- **InGameMenuParent:**
    - **Location:** `com.example.demo.UI.screens.InGameMenuParent`
    - **Description:** Abstract parent class for in-game overlays such as the pause menu and power-up menu, ensuring consistent UI behavior.

- **PauseMenu:**
    - **Location:** `com.example.demo.UI.menu.PauseMenu`
    - **Description:** Pauses gameplay and overlays a menu with options to resume, restart, or quit, providing players flexibility during gameplay.

- **PowerUpMenu:**
    - **Location:** `com.example.demo.UI.menu.PowerUpMenu`
    - **Description:** Displays available power-up options in real-time, allowing players to activate them during gameplay dynamically.It also showcases the price for each Power Up for user to plan their moves.

---
## *Modified Java Classes*

---

### <u>LevelParent.java</u>

The `LevelParent` class underwent significant modifications to improve maintainability, scalability, and modularity.

#### Refactor Highlights

1. **Actor Management Refactor**
    - **Before:** Actor-related operations (adding, updating, and removing) were handled directly within `LevelParent` using separate lists for friendly units, enemies, projectiles, and coins.
    - **Changes:**
        - Replaced individual fields like `friendlyUnits`, `enemyUnits`, `userProjectiles`, etc., with calls to `ActorManager`.
        - Delegated actor updates and cleanup to `ActorManager` via its `updateActors()` and `removeDestroyedActors()` methods.
        - Introduced the `handleAllActors()` method in `ActorManager` to streamline operations for all actors.
        - Resulted in cleaner `LevelParent` code, encapsulation, adherence to the Single Responsibility Principle, and improved scalability and maintainability.

2. **Collision Management Refactor**
    - **Before:** Collision handling logic (e.g., plane-to-plane, projectiles-to-enemies) was embedded within `LevelParent`, leading to cluttered code.
    - **Changes:**
        - Added a `CollisionManager` instance to `LevelParent` and delegated all collision-related logic to it via `handleAllCollisions()`.
        - Unified handling of plane, projectile, and coin collisions within `CollisionManager`.

3. **Input Handling Refactor**
    - **Before:** Input handling (key presses and releases) was defined directly in `initializeBackground()` in `LevelParent` and overridden in certain levels.
    - **Changes:**
        - Added an `InputHandlingManager` instance to `LevelParent` to manage user inputs.
        - Introduced a `MovementMode` enum:
            - `VERTICAL_ONLY`: Supports only UP, DOWN, and SPACE keys.
            - `FULL`: Adds LEFT and RIGHT movement for specific levels.
        - Configured movement modes for each level, reducing redundant logic.
        - Leveraged the Strategy Pattern for flexible input behavior per level by setting the appropriate movement mode.
        - Resulted in cleaner and reusable input logic.

4. **Game Loop Refactor**
- **Before:** The game update loop was managed using `Timeline` directly in `LevelParent`.
- **Changes:**
    - Introduced the `GameLoop` class to handle the timeline and frame updates, invoking the `update()` method for all registered `Updatable` instances.
    - `LevelParent` implements the `Updatable` interface and registers itself with the `GameLoop`.
    - **Decoupling Logic:**
        - **ActorManager** and **CollisionManager** now implement the `Updatable` interface and are registered directly with the `GameLoop`.
        - As a result, `LevelParent` no longer manages actor and collision updates directly. This ensures that `LevelParent` is independent of these specific logics, improving modularity and maintainability.
    - Decoupled timing mechanisms from game logic, enabling a reusable `GameLoop` for other levels or future projects.


5. **Game State Management**
    - **Centralization with `GameStateManager`:**
        - **Before:** `LevelParent` contained game-over logic, directly checking conditions for losing or completing a level.
        - **Changes:**
            - Added a `GameStateManager` instance to `LevelParent`.
            - Replaced direct game-over logic in `checkIfGameOver()` with calls to `GameStateManager`.

6. **Controllable Level Interface**
    - Added a new interface, `ControllableLevel`, to standardize level behavior.
    - **Methods:**
        - `initializeScenario()`: Initializes the level’s scene.
        - `fireProjectile()`: Handles user projectile firing logic.
        - `getRoot()`: Provides the root node of the level for external access.
    - Ensures each level adheres to a defined contract for controlling gameplay elements and helps decouple logic.

7. **Additional Enhancements**
    - **Star System Integration:** Added calls to `StarManager` for calculating and tracking stars based on bullets used and optimal thresholds.
    - **Spawn Coins Logic Added**

---

### <u>Controller.java</u>

The `Controller` class was refactored to simplify level transitions and adhere to modern design principles.

1. **Removal of Reflection-Based Level Initialization**
    - **Before:**
        - Levels were initialized using reflection with the `goToLevel` method, which dynamically instantiated level classes.
        - Relied on `Class.forName` and constructor invocation, making the code prone to runtime errors and hard to maintain.
    - **After:**
        - Introduced a `LevelManager` to handle level transitions and initialization.
        - Replaced `goToLevel` with direct calls to `LevelManager.startFirstLevel()` or `LevelManager.goToNextLevel()` for managing levels.

2. **Transition from Observable and Observer to MyObserver**
    - **Before:**
        - Used Java’s deprecated `Observable` and `Observer` APIs to notify the `Controller` of level completion events.
    - **After:**
        - Replaced the deprecated APIs with a custom observer pattern using `MyObserver`. This modern approach avoids reliance on deprecated classes, ensuring forward compatibility.
        - The `Controller` now implements the `MyObserver` interface, which defines a simple and reusable mechanism for receiving level-completion notifications.
        - The `onLevelWin(String nextLevel)` method listens for events and delegates the transition logic to the `LevelManager` (`goToNextLevel` for "NEXT").
        - The updated approach decouples the `Controller` from legacy Java classes, making the code more maintainable and adhering to modern Java practices.

3. **Integration with StageManager**
    - **Before:**
        - `Controller` directly accessed and managed the `Stage` instance, creating tight coupling.
    - **After:**
        - The `Stage` instance is now retrieved via `StageManager`, ensuring consistency and decoupling the `Controller` from direct stage management. This adheres to the Single Responsibility Principle.

---

### <u>Main.java</u>

1. **Integration with StageManager**
    - **Before:**
        - The `Main` class directly managed the `Stage` instance and passed it to the `Controller`.
    - **After:**
        - The `Stage` is now set centrally in the `StageManager`, which handles stage-related operations across the game.

2. **Main Menu Integration**
    - **Before:**
        - The game launched directly into gameplay through the `Controller`.
    - **After:**
        - Added a `MainMenu` class to provide a user-friendly entry point.
        - The game now starts at the main menu, allowing navigation to gameplay, customization, and instructions.

3. **SoundManager Integration**
    - **Before:**
        - Background music was not initialized at the start of the game.
    - **After:**
        - Integrated `SoundManager` to play background music upon application launch.

---

### <u>ActiveActor.java</u>

The `ActiveActor` and `ActiveActorDestructible` classes were merged into a single class to simplify the hierarchy, reduce redundancy, and eliminate unnecessary abstraction.

1. **Consolidation of Classes**
    - **Before:**
        - `ActiveActor` was a base class managing image rendering and movement logic.
        - `ActiveActorDestructible` extended `ActiveActor` and added destructible behavior with the `Destructible` interface.
    - **After:**
        - Merged `ActiveActor` and `ActiveActorDestructible` into a single `ActiveActor` class consolidating all logic in one class.
        - Implemented the `Destructible` interface directly in `ActiveActor`.

2. **Enhanced Resource Handling**
    - **Image Resource Management:**
        - Added a `String imageName` field to store the image name for better traceability and debugging.
        - Introduced robust error handling:
            - Throws an `IllegalArgumentException` if the image resource cannot be found.
---

### <u>EnemyPlane.java and Boss.java</u>

The `EnemyPlane` and `Boss` classes were significantly refactored to improve code reuse, maintainability, and flexibility by introducing a shared `EnemyParent` base class.

1. **Introduction of EnemyParent Base Class**
    - **Before:**
        - `EnemyPlane` and `Boss` had redundant code for shared behaviors like movement, firing projectiles, and health management.
    - **After:**
        - Both classes now inherit from the new `EnemyParent` class, which encapsulates shared functionality such as:
            - Managing projectile creation through the `ProjectileFactory`.
            - Streamlining movement and destruction logic.

2. **Use of ProjectileFactory**
    - **Before:**
        - Projectiles were created directly in each class with hardcoded logic.
    - **After:**
        - Leveraged the `ProjectileFactory` for standardized projectile creation, reducing duplication and ensuring consistency.

3. **Refined Movement Patterns**
    - **Before:**
        - Movement logic for `Boss` was tightly coupled to its class, making it hard to extend.
    - **After:**
        - Introduced flexible movement patterns in `EnemyParent` for vertical and horizontal movements.
        - `Boss` overrides `updatePositionWhenActive` to implement unique move patterns while leveraging shared bounds-checking logic.

4. **Integration with LevelParent**
    - Both `EnemyPlane` and `Boss` now interact with their parent level (`LevelParent`) for tasks like projectile instantiation and boundary calculations.
    - **Benefits:**
        - Improved decoupling by delegating level-specific tasks to `LevelParent`.

---

### <u>ProjectileParent.java, BossProjectile.java, UserProjectile.java</u>

1. **Name Change of Parent Class**
    - **Before:** The parent class was named `Projectile`.
    - **After:** Renamed to `ProjectileParent` for clarity and to align with naming conventions used across the project (e.g., `EnemyParent`).

2. **Standardization of updatePosition**
    - Unified projectile position updates in `ProjectileParent`:
        - `ProjectileParent.updatePosition()` now includes a pause condition using `GameStateManager` to halt updates when the game is paused.
        - Removed individual `updatePosition` implementations from `UserProjectile` and `BossProjectile`, as the logic is now centralized in `ProjectileParent`.

3. **Consistency in Constructors**
    - Updated constructors for both projectiles to utilize the shared velocity parameter in `ProjectileParent`, ensuring consistency across all projectile types.

---

### <u>LevelView.java and LevelViewLevelTwo.java</u>

The `LevelView` and `LevelViewLevelTwo` classes were significantly refactored and replaced by the `LevelViewParent` and its specialized subclasses.
`LevelView` was renamed to `LevelViewParent` to maintain uniformity among Super classes.
1. **Introduction of LevelViewParent**
    - **Before:**
        - `LevelView` contained all UI-related logic, including heart displays, win/loss screens, and shield mechanics.
        - Specialized behavior for specific levels was hardcoded in subclasses like `LevelViewLevelTwo`.
    - **After:**
        - Introduced `LevelViewParent` as an abstract base class.
        - Encapsulates common UI elements and shared functionality (e.g., heart display, coin and bullet counters, pause/power-up menus).
        - Subclasses like `LevelViewLevelTwo` override specific methods for level-specific behavior, enhancing Separation of Concerns.

2. **Enhanced UI Elements**
    - **Added Dynamic UI Components:**
        - Coin Counter: Displays coins dynamically with updates from `CoinSystemManager`.
        - Bullet Counter: Tracks remaining bullets using `BulletSystemManager`.
        - Shield Timer: Provides a visual countdown for shield duration.
        - Pause and Power-Up Menus: Integrated buttons for pausing gameplay and accessing power-ups.
    - **Improved Styling:**
        - Enhanced visual appeal with gradient effects, drop shadows, and positioning tweaks for all UI elements.

3. **Modularization with LevelViewParent**
    - **Abstract Methods:**
        - Introduced `initializeWinningParameter()` and `updateWinningParameter()` to allow levels to define win conditions and progress indicators independently.
    - **Root Node Management:**
        - Common UI elements (e.g., heart display, coin and bullet counters) are now managed centrally in `LevelViewParent` and automatically added to the root group.

4. **Refactoring of LevelViewLevelTwo**
    - **Before:**
        - `LevelViewLevelTwo` hardcoded shield mechanics and position updates.
    - **After:**
        - Delegated shield mechanics to a specialized `ShieldImage` class and integrated it with the parent level through `LevelViewParent`.
        - Added alert messages for shield activation using `AlertManager`.

5. **Improved Integration with Managers**
    - **System Managers:**
        - Integrated `CoinSystemManager`, `BulletSystemManager`, and `SoundManager` for dynamic updates to UI elements and audio feedback.
    - **Stage Management:**
        - Utilized `StageManager` for consistent stage handling across all levels and menus.

---

### <u>WinImage.java</u>

The `WinImage` class has undergone significant refactoring to provide enhanced functionality, modularity, and a better user experience.

1. **Transition from ImageView to Pane**
    - **Before:**
        - `WinImage` extended `ImageView`, limiting its functionality to simply displaying an image.
        - Relied on external classes to manage additional elements.
    - **After:**
        - `WinImage` now extends `Pane`, allowing it to act as a container for multiple UI elements, including images, buttons, and star displays.
        - Using `Pane` provides the flexibility to add and organize various UI components in a single cohesive layout.

2. **Addition of Gameplay Elements**
    - **Final Stars Display:**
        - Introduced a `StarDisplay` that shows the player's final star count, calculated using `StarManager`.
        - Positioned dynamically on the screen for a polished presentation.
    - **Play Again and Quit Buttons:**
        - Play Again: Restarts the game, resetting the state and playing background music.
        - Quit: Exits the game cleanly.

3. **Improved Positioning and Styling**
    - **Dynamic Positioning:**
        - The win image is centered dynamically based on screen width and height, ensuring consistent alignment across different devices.
        - Refined image scaling with `PreserveRatio` for consistent aspect ratios.
        - Buttons and star displays are positioned with precise calculations to align with the overall aesthetic.

4. **Integration with Sound Manager**
    - Plays a celebratory background track and sound effects upon winning.
    - Resets the music if the player chooses to play again.

---

### <u>GameOverImage.java</u>

The `GameOverImage` class was refactored to improve interactivity, design, and maintainability.

1. **Transition from ImageView to Pane**
    - **Before:**
        - The `GameOverImage` class was a simple `ImageView` displaying a static image.
        - No interactive or dynamic functionality was included.
    - **After:**
        - `GameOverImage` now extends `Pane`, allowing it to contain multiple UI elements such as images, buttons, and a dimmed background overlay.
        - Extending `Pane` provides the flexibility to manage additional UI components and interactions within the game over screen.

2. **Addition of Interactive Elements**
    - **Play Again Button:**
        - A `PlayAgainButton` allows players to restart the game, resetting the bullet count using `BulletSystemManager`.
        - On click, it launches the game from Level 1 dynamically using the `Controller` class.
    - **Quit Button:**
        - A `QuitButton` lets players exit the game.
        - Dynamically retrieves the stage and closes the application cleanly.

3. **Dimmed Background Overlay**
    - **Before:**
        - No background was provided, which could make the game over image appear less polished.
    - **After:**
        - A dimmed black background using a `Rectangle` overlay was added, with transparency (`opacity = 0.7`) for a visually appealing effect.

4. **Dynamic Positioning and Styling**
    - **Before:**
        - Hardcoded positioning for the game over image.
    - **After:**
        - The game over image is dynamically centered based on screen width and height.
        - Buttons are precisely positioned relative to the screen for a balanced layout.


---

### <u>LevelOne.java</u>

1. **Enemy Spawning:**
    - Replaced direct instantiation of enemies with `EnemyFactory`, improving modularity using the Factory design pattern.

2. **Level View:**
    - Switched from generic `LevelView` to specific `LevelViewLevelOne` for tailored UI enhancements.

3. **Kill Tracking:**
    - Refactored to use centralized `numberOfKills` in `LevelParent`, reducing redundancy.

4. **Optimal Bullets:**
    - Added `calculateOptimalBullets()` to align with the star system.

5. **Improved Modularity:**
    - Centralized spawning constraints (e.g., toolbar height), dynamic file paths for the background, and reusable logic.

**Core Gameplay:**
- No changes to gameplay mechanics—enhancements focused on maintainability and modularity.

---

### <u>LevelTwo.java</u>

1. **Boss Initialization:**
    - **Old:**
        - The boss was directly instantiated as `new Boss()` within the constructor.
    - **New:**
        - Uses `EnemyFactory` to create the boss dynamically with parameters like position and health, aligning with the Factory Design Pattern.

2. **Boss Health Tracking:**
    - **Old:**
        - No UI to track or display boss health.
    - **New:**
        - Added a `Label` to display the boss's health dynamically, styled with custom fonts and gradients.
        - `initializeWinningParameter()` sets up the boss health label.
        - `updateWinningParameter()` dynamically updates the health label in real time using `Platform.runLater()` for thread safety.

3. **Level View:**
    - **Old:**
        - Used `LevelViewLevelTwo` directly, without additional configuration or references to shields.
    - **New:**
        - Enhanced `LevelViewLevelTwo` for shield tracking. Added methods in the view to show, hide, and update the shield based on the boss's state.
        - `updateShieldImage()` handles shield visuals and ensures proper synchronization with the boss's position.

4. **Game Flow Enhancements:**
    - **Old:**
        - The win condition was hardcoded directly in `checkIfGameOver()`.
    - **New:**
        - Refactored win condition into `userHasReachedKillTarget()`, centralizing logic in `LevelParent` for better maintainability.
        - `checkIfGameOver()` is simplified to rely on the refactored logic.

5. **Bullet Efficiency:**
    - **New Addition:**
        - Introduced `calculateOptimalBullets()` to define an optimal bullet count for the level, used for star evaluations.

6. **Scene Updates:**
    - **Old:**
        - No explicit handling for shield or boss-specific updates in the game loop.
    - **New:**
        - `updateSceneFurther()` now manages boss-specific updates like shield visuals and health tracking, decoupling these details from generic logic.

7. **UI Styling:**
    - **New:**
        - Custom styles and layout for boss health tracking, improving user experience and visual engagement.

---

### <u>ShieldImage.java</u>

The updated `ShieldImage` introduces minor enhancements, such as dynamic position updates, improved visibility handling, and a smaller, more polished image. These changes improve gameplay immersion and compatibility with other elements in the game.

1. **Position Updates:**
    - **New:** Added `updatePosition()` method to dynamically update the shield's position, aligning it with its corresponding actor (e.g., user plane or boss).

2. **Improved Visibility Management:**
    - **Old:** Shield visibility changes were limited to basic methods like `showShield()` and `hideShield()`.
    - **New:** Added `toFront()` in `showShield()` to ensure the shield is rendered above other elements when visible.

---

### <u>HeartDisplay.java</u>

The new `HeartDisplay` remains mostly unchanged, with the `addHeart()` method as the primary enhancement, allowing dynamic updates to the heart count during gameplay. This aligns with improved user experience and health mechanics.

1. **New Method:**
    - Added `addHeart()`:
        - Dynamically adds a heart to the container.
        - Increases `numberOfHeartsToDisplay`.

2. **Improved Robustness:**
    - Used `Objects.requireNonNull()` in `initializeHearts()` and `addHeart()` to prevent null pointer exceptions when loading the heart image.

## *Overall Changes*

---

1. **Image Updates and Resizing**  
    - **Image Cropping:** Adjusted bounding boxes for user planes, enemies, and projectiles to resolve oversized collision issues.
    - **Visual Updates:**
        - User planes, hearts, shields, and backgrounds were replaced with modern, high-quality assets.
        - All image sizes were standardized for consistent visuals.

2. **Bug Fixes**
    - Fixed critical issues:
        - **Boss Health Tracking:** Resolved null pointer exceptions in boss health label updates.
        - **Shield Logic:** Corrected shield activation and deactivation behavior.
        - **Collision Bugs:** Addressed issues causing false collisions due to improperly sized images.
        - **Enemy Penetration:** Prevented the user from taking damage when penetrating the left side of the screen, making the game more plausible and playable.
        - **Collision Handling:** Fixed numerous collision-related issues between planes and their relation to the kill count, making the game logical, playable, and winnable.

3. **Package Organization**
    - Classes reorganized into structured packages (e.g., actors, managers, UI, levels), improving maintainability.

4. **Encapsulation and Maintenance**
    - Renamed and refactored classes for clarity and functionality.
    - Encapsulated fields across various classes to ensure data integrity.
    - Removed unused resources to streamline the codebase.

---

## *Unexpected Problems*

---

### <u>Shield Activation Issues</u>
- **Description:**
    - During the implementation of the shield power-up, repeated activation warnings were logged in the console. This occurred because the shield activation logic was inadvertently triggered multiple times for a single activation.
- **Resolution:**
    - Debugging flags were introduced to track whether the shield was already active, preventing redundant activations.
    - The logic now checks the flag before activating the shield, ensuring smooth gameplay without unnecessary warnings.

---

### <u>Boss Health Label Null Exception</u>
- **Description:**
    - In Level Two, a null pointer exception occurred when attempting to display the boss’s health bar. The health label was not properly initialized, leading to runtime crashes.
- **Resolution:**
    - The issue was resolved by initializing the health label within the level-specific setup methods. The boss health label was placed directly inside the Level Two class, where the retrieval of boss health generated by the enemy factory is not null.
    - Additional safeguards were added to ensure the health logic is only accessed after initialization, preventing similar issues in future levels.

---

### <u>Cloud Rendering Issue</u>
- **Description:**
    - Clouds designed to enhance the aesthetic of Level Five were not appearing during gameplay. The rendering logic failed to properly add the cloud objects to the scene graph.
- **Resolution:**
    - The root cause was a layering issue where the clouds were being added behind other elements, rendering them invisible to the player.
    - Adjustments were made to the rendering order, ensuring the clouds were placed in the correct layer of the scene.

---
### <u> The Java Heap Error  in Level Four Development </u>

- **Error Description**
While developing **Level Four**, an issue was encountered where the game would crash with a **Java heap space error**. This occurred due to the excessive accumulation of `BombProjectile` and `FlarePowerUp` instances, as these objects were continuously spawned but not properly removed after they were destroyed or left the screen.

-**Symptoms**
- The game slowed down significantly after prolonged gameplay.

#### **Cause** 
- Objects like `BombProjectile` and `FlarePowerUp` were continuously added to the game but were never properly removed from memory after being destroyed or leaving the visible game area.
- This led to memory overflow, as these objects remained in the `Group` node and in the internal `List` structures within the `LevelParent` or `ActorManager`.

#### **Solution**
The issue was resolved by implementing proper cleanup logic for the `BombProjectile` and `FlarePowerUp` objects, ensuring they were removed from memory when they were no longer needed.

#### **Steps Taken**

#### 1. **Tracked Objects with `ActorManager`**

#### 2. **Implemented Cleanup Logic**
- Modified the `ActorManager` to include a `removeDestroyedActors()` method that:

#### 3. **Removed Objects When Off-Screen**
- Updated the `updateActor()` method in `BombProjectile` and `FlarePowerUp` to check their positions.
- If an object moved off-screen, it flagged itself as "destroyed" using a boolean property.

#### 5. **Verified with Testing**
- Stress-tested the game by increasing the spawn rate of bombs and flares.
- Monitored memory usage to confirm that it remained stable over time, even with heavy object creation and destruction.

---

### <u>Meteor Behaviour Issues</u>
- **Description:**
    - Meteors introduced in the showdown phase of Level Five were intended to spawn diagonally from the top-right corner. However, their trajectories were inconsistent, often appearing stationary or moving in unexpected directions.
- **Resolution:**
    - Debugging revealed a problem with the velocity vectors assigned to the meteors. Updates were made to the spawn logic, recalculating the velocity for diagonal movement.
    - While some improvements were implemented, further refinements are still required to achieve the intended behavior.

---

### <u>Rain Effect Issue</u>
- **Description:**
    - A rain effect designed to add dynamic visuals to Level Five was commented out due to rendering problems. Raindrops were not appearing on the screen despite being active in the game logic.
- **Resolution:**
    - The issue stemmed from incorrect positioning and layering of the rain container in the scene graph.
    - While debugging was in progress, the feature was temporarily disabled. The feature remains commented out in the code.
---
## *Design Patterns Followed in the Game*

---

1. **Strategy Pattern**
    - Utilized in input handling (`MovementMode`) for flexible, level-specific controls.

2. **Observer Pattern**
    - Integrated into the Level Winning mechanism, `BulletSystemManager`, and `CoinSystemManager` to notify UI components dynamically about updates in bullet and coin counts.

3. **Factory Pattern**
    - Used for creating user planes, enemy planes, and projectiles (`UserPlaneFactory`, `EnemyFactory`, `ProjectileFactory`), ensuring consistency in initialization. This ensures objects are instantiated only when needed.

4. **Singleton Pattern**
    - Applied in classes like `BulletSystemManager`, `CoinSystemManager`, `GameStateManager`, and `StageManager` to ensure only one instance manages global game data and operations.
    - Simplifies access and ensures consistency throughout the game.

5. **Single-Spot Mechanism**
    - Centralized alert displays (`AlertManager`) and power-up purchases (`PowerUpManager`) to provide a unified point of control for related operations, enhancing maintainability.

6. **Single Responsibility Principle**
    - Delegated tasks to dedicated classes:
        - **`CollisionManager`:** Handles all collision logic.
        - **`ActorManager`:** Manages game actors like coins, enemies, and projectiles.
        - **`GameStateManager`:** Manages game states (e.g., paused, running).

7. **Updatable Interface**
    - Standardized update logic across `LevelParent`, `ActorManager`, and `CollisionManager` to streamline game loop operations.
