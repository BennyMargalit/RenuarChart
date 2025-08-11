# Payment Tracker

A C# console application to help you track monthly payments, avoid double payments, and never miss a due date again!

## Features

- **Payment Management**: Add, edit, delete, and track all your monthly payments
- **Smart Reminders**: Get alerts for overdue payments and upcoming due dates
- **Recurring Payments**: Automatically create next payment for recurring bills
- **Payment History**: Keep track of all paid and unpaid payments
- **Data Persistence**: Your payments are automatically saved and loaded
- **Summary Reports**: View payment statistics and totals

## How It Works

The app automatically:
- Shows reminders for overdue payments
- Highlights payments due within 7 days
- Creates next recurring payment when you mark one as paid
- Saves all data to your local machine
- Provides a clear overview of your payment status

## Getting Started

### Prerequisites
- .NET 8.0 SDK or later
- Windows, macOS, or Linux

### Installation & Running

1. **Clone or download** the project files
2. **Open terminal/command prompt** in the project directory
3. **Build the project**:
   ```bash
   dotnet build
   ```
4. **Run the application**:
   ```bash
   dotnet run
   ```

## Usage Guide

### Main Menu Options

1. **View All Payments** - See all your payments in chronological order
2. **Add New Payment** - Create a new payment entry
3. **Edit Payment** - Modify existing payment details
4. **Delete Payment** - Remove a payment from your tracker
5. **Mark Payment as Paid** - Record when you've paid a bill
6. **Show Reminders** - View overdue and upcoming payments
7. **Show Upcoming Payments** - See payments due in the next 30 days
8. **Show Overdue Payments** - View all overdue payments
9. **Payment Summary** - Get statistics and totals
0. **Exit** - Save and close the application

### Adding a Payment

When adding a new payment, you'll be prompted for:
- **Name**: What the payment is for (e.g., "Electric Bill", "Netflix")
- **Amount**: How much you owe
- **Due Date**: When the payment is due (MM/DD/YYYY format)
- **Recurring**: Whether this is a monthly/quarterly/yearly bill
- **Notes**: Optional additional information

### Recurring Payments

For recurring bills (like subscriptions), the app will:
- Ask if it's recurring and how often
- Automatically create the next payment when you mark one as paid
- Keep your payment schedule going without manual input

### Payment Status

Payments are automatically categorized as:
- **Paid**: Payment has been completed
- **Overdue**: Payment is past due
- **Due Soon**: Payment is due within 7 days
- **Upcoming**: Payment is due later

## Data Storage

Your payment data is automatically saved to:
- **Windows**: `%APPDATA%\PaymentTracker\payments.json`
- **macOS**: `~/Library/Application Support/PaymentTracker/payments.json`
- **Linux**: `~/.config/PaymentTracker/payments.json`

The data is stored in JSON format and can be backed up or transferred between machines.

## Tips for Best Use

1. **Add all recurring bills** as recurring payments to avoid forgetting them
2. **Use descriptive names** for payments to easily identify them
3. **Check reminders regularly** to stay on top of upcoming payments
4. **Mark payments as paid** immediately after paying to keep your records current
5. **Use notes** to add account numbers, confirmation codes, or other details

## Example Use Cases

- **Monthly Bills**: Rent, utilities, phone, internet, insurance
- **Subscriptions**: Netflix, Spotify, gym memberships, software licenses
- **Annual Payments**: Car registration, property taxes, insurance renewals
- **Quarterly Payments**: Estimated taxes, business expenses

## Troubleshooting

- **Data not saving**: Check if the app has write permissions to the data directory
- **Date format errors**: Use MM/DD/YYYY format (e.g., 12/25/2024)
- **Amount errors**: Enter only numbers and decimal points (e.g., 99.99)

## Future Enhancements

Potential features for future versions:
- Email/SMS reminders
- Payment categories and tags
- Export to CSV/PDF
- Multiple currency support
- Payment splitting for shared expenses
- Integration with banking apps

## Contributing

Feel free to suggest improvements or report issues. This is a personal project designed to solve real payment tracking problems.

---

**Never pay double again!** 🎯