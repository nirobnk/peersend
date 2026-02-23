# PeerSend UI Improvements

## Summary of Changes

The UI has been completely redesigned with a modern, professional look and feel.

### Key Improvements

#### 1. **Visual Design**

- **Gradient Background**: Beautiful purple gradient (from #667eea to #764ba2) across all screens
- **Modern Card Layout**: White cards with rounded corners and shadows for better content organization
- **Consistent Color Scheme**: Professional color palette throughout the application
- **Better Typography**: Larger, bolder titles with proper font hierarchy

#### 2. **Welcome Screen**

- Clean, centered layout with large title "PeerSend"
- Descriptive subtitle explaining the app's purpose
- Icon indicators (📁) for visual appeal
- Prominent "Get Started" button with hover effects

#### 3. **Mode Selection Screen**

- Visual emoji icons (📤 for Send, 📥 for Receive)
- Large, card-style buttons for better usability
- Descriptive labels under each option
- Symmetric layout for better balance

#### 4. **Sender Screen**

- Organized card layout with clear sections
- Labeled input fields with helpful prompts
- "Choose File" button with distinct orange color
- Status messages with emoji indicators (✅, ❌, 📤)
- Tip labels to guide users
- Back button for easy navigation

#### 5. **Receiver Screen**

- Similar card-based layout for consistency
- Clear file name input with placeholder text
- "Choose Folder" button for destination selection
- Status indicators with emojis
- Helpful tips about file extensions
- Blue-themed "Start Receiving" button

#### 6. **Enhanced CSS Styling**

Created a comprehensive `styles.css` file with:

- **Button Styles**:
  - Primary buttons (green gradient)
  - Secondary buttons (blue gradient)
  - Action buttons (white with shadow)
  - Small buttons (transparent with border)
  - Choose buttons (orange gradient)
- **Hover Effects**: Buttons scale up on hover for better feedback
- **Input Fields**: Rounded corners with focus states
- **Labels**: Multiple label styles for different contexts
- **Card Containers**: Elevated white cards with shadows

#### 7. **User Experience Improvements**

- Better error messages with visual indicators
- Status updates during file transfer
- Clearer prompt text in input fields
- Validation messages for empty fields
- Proper initialization of UI elements
- Non-resizable window for consistent experience

### Technical Changes

#### Files Modified:

1. **Welcome.fxml** - Redesigned with VBox layout
2. **selectBetween.fxml** - New card-based selection screen
3. **sender.fxml** - Modern form layout with cards
4. **receiver.fxml** - Consistent design with sender screen
5. **Main.java** - Added window title and made non-resizable
6. **ControllerSender.java** - Improved validation and status messages
7. **ControllerReceiver.java** - Enhanced error handling and feedback

#### Files Created:

1. **styles.css** - Comprehensive stylesheet with all custom styles

### Before & After

**Before:**

- Plain white background with default JavaFX styling
- Minimal spacing and layout
- Basic buttons with no hover effects
- Generic error messages
- Cluttered positioning using AnchorPane

**After:**

- Beautiful gradient backgrounds
- Professional card-based layouts
- Interactive buttons with animations
- Clear status messages with emojis
- Organized VBox/HBox layouts with proper spacing
- Consistent design language across all screens

### Color Palette

- **Background**: Purple gradient (#667eea → #764ba2)
- **Primary Buttons**: Green gradient (#4CAF50 → #45a049)
- **Secondary Buttons**: Blue gradient (#2196F3 → #1976D2)
- **Choose Buttons**: Orange gradient (#FF9800 → #F57C00)
- **Text**: White and dark purple for contrast
- **Cards**: White with subtle shadows

### How to Run

```bash
mvn clean javafx:run
```

The application will open with the new modern UI design!
