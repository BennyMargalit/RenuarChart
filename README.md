# Payment Reminder Android App

A modern Android app built with Kotlin and Jetpack Compose that helps you track payments and expenses with camera-based receipt scanning.

## Features

### 💳 Payment Management
- **Add Payments**: Create recurring or one-time payment reminders
- **Payment Tracking**: View upcoming, overdue, and paid payments
- **Smart Notifications**: Get reminded of upcoming payments
- **Categories**: Organize payments by category (Utilities, Rent, Insurance, etc.)

### 📱 Expense Tracking
- **Monthly Overview**: See your total monthly expenses at a glance
- **Category Breakdown**: Track spending by category
- **Receipt Scanning**: Use camera to scan receipts and automatically extract expense data
- **Manual Entry**: Add expenses manually with detailed information

### 📸 Camera Integration
- **Receipt Scanning**: Take photos of receipts to automatically extract:
  - Merchant name
  - Amount
  - Date
  - Raw text for verification
- **Smart Parsing**: AI-powered text recognition to parse receipt data
- **Preview & Confirm**: Review extracted data before adding to expenses

### 🔔 Smart Notifications
- **Daily Reminders**: Check for upcoming payments every day
- **Overdue Alerts**: Get notified about overdue payments
- **Customizable Timing**: Notifications sent at 9 AM daily

## Technical Architecture

### Built With
- **Kotlin** - Modern Android development language
- **Jetpack Compose** - Modern UI toolkit
- **Room Database** - Local data persistence
- **Hilt** - Dependency injection
- **CameraX** - Camera functionality
- **ML Kit** - Text recognition for receipt scanning
- **WorkManager** - Background notification scheduling

### Architecture Pattern
- **MVVM** (Model-View-ViewModel)
- **Repository Pattern** for data access
- **Dependency Injection** with Hilt
- **Reactive Programming** with Kotlin Flow

## Project Structure

```
app/
├── src/main/java/com/paymentreminder/app/
│   ├── data/
│   │   ├── entity/          # Room database entities
│   │   ├── dao/             # Data access objects
│   │   ├── database/        # Database configuration
│   │   └── repository/      # Repository implementations
│   ├── ui/
│   │   ├── screens/         # Compose screens
│   │   ├── components/      # Reusable UI components
│   │   ├── viewmodel/       # ViewModels
│   │   ├── navigation/      # Navigation setup
│   │   └── theme/           # App theming
│   ├── camera/              # Camera and receipt parsing
│   ├── notification/        # Notification management
│   └── di/                  # Dependency injection modules
```

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24+ (Android 7.0)
- Kotlin 1.9.20+

### Installation
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on device or emulator

### Permissions Required
- **Camera**: For scanning receipts
- **Notifications**: For payment reminders
- **Storage**: For saving receipt images

## Usage

### Adding Payments
1. Tap the "+" button on the home screen
2. Fill in payment details:
   - Title (e.g., "Electric Bill")
   - Amount
   - Category
   - Due date
   - Description (optional)
   - Recurring settings (optional)
3. Tap "Add Payment"

### Tracking Expenses
1. Go to the Expenses tab
2. Use the camera to scan receipts or add manually
3. Review and confirm extracted data
4. View monthly summaries and category breakdowns

### Camera Scanning
1. Tap the camera icon
2. Point camera at receipt
3. Tap capture button
4. Review extracted data
5. Confirm to add as expense

## Database Schema

### Payments Table
- `id`: Primary key
- `title`: Payment name
- `amount`: Payment amount
- `dueDate`: Due date
- `category`: Payment category
- `description`: Optional description
- `isPaid`: Payment status
- `isRecurring`: Recurring flag
- `recurringInterval`: Recurring frequency
- `createdAt`: Creation timestamp
- `receiptImagePath`: Optional receipt image

### Expenses Table
- `id`: Primary key
- `title`: Expense name
- `amount`: Expense amount
- `category`: Expense category
- `date`: Expense date
- `description`: Optional description
- `receiptImagePath`: Optional receipt image
- `createdAt`: Creation timestamp

## Future Enhancements

- [ ] Export data to CSV/PDF
- [ ] Budget tracking and alerts
- [ ] Multiple currency support
- [ ] Cloud backup and sync
- [ ] Advanced analytics and charts
- [ ] Payment method tracking
- [ ] Bill splitting features
- [ ] Integration with banking APIs

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For issues and questions, please create an issue in the GitHub repository.