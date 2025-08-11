# 🎯 Payment Tracker - App Preview & Demo

## 🚀 **Welcome Screen**
```
Welcome to Payment Tracker!
Your personal assistant to never pay double again!

⚠️  REMINDERS:
  • Car Insurance: $450.00 - OVERDUE
  • Rent: $1200.00 - Due in 1 days

=== PAYMENT TRACKER ===
1. View All Payments
2. Add New Payment
3. Edit Payment
4. Delete Payment
5. Mark Payment as Paid
6. Show Reminders
7. Show Upcoming Payments
8. Show Overdue Payments
9. Payment Summary
0. Exit
======================
Enter your choice:
```

## 📋 **1. View All Payments**
```
=== ALL PAYMENTS ===
ID   Name                 Amount     Due Date    Status     Notes
--------------------------------------------------------------------------------
4    Car Insurance       $450.00    11/20/2024  Overdue    Quarterly payment - due soon!
3    Netflix Subscription $15.99    11/28/2024  Paid       Premium plan
1    Rent                $1200.00   12/01/2024  Due Soon   Monthly rent payment
5    Phone Bill          $65.00     12/10/2024  Upcoming   Verizon - unlimited plan
2    Electric Bill       $85.50     12/15/2024  Upcoming   Utility bill - account #12345
```

## ➕ **2. Add New Payment**
```
=== ADD NEW PAYMENT ===
Payment Name: Gym Membership
Amount: $45.00
Due Date (MM/DD/YYYY): 12/05/2024
Is this a recurring payment? (y/n): y
Recurrence type:
1. Monthly
2. Quarterly
3. Yearly
Enter choice: 1
Notes (optional): Planet Fitness - basic plan

Payment 'Gym Membership' added successfully with ID: 6
```

## 🔍 **6. Show Reminders**
```
=== REMINDERS ===
ID   Name                 Amount     Due Date    Status     Notes
--------------------------------------------------------------------------------
4    Car Insurance       $450.00    11/20/2024  Overdue    Quarterly payment - due soon!
1    Rent                $1200.00   12/01/2024  Due Soon   Monthly rent payment
6    Gym Membership      $45.00     12/05/2024  Due Soon   Planet Fitness - basic plan
```

## 📊 **9. Payment Summary**
```
=== PAYMENT SUMMARY ===
Total Payments: 6
Paid: 1
Unpaid: 5
Overdue: 1
Due Soon (≤7 days): 3
Total Amount Paid: $15.99
Total Amount Due: $1,835.50
Total Overdue Amount: $450.00
```

## 💰 **5. Mark Payment as Paid**
```
=== MARK PAYMENT AS PAID ===
ID   Name                 Amount     Due Date    Status     Notes
--------------------------------------------------------------------------------
4    Car Insurance       $450.00    11/20/2024  Overdue    Quarterly payment - due soon!
1    Rent                $1200.00   12/01/2024  Due Soon   Monthly rent payment
5    Phone Bill          $65.00     12/10/2024  Upcoming   Verizon - unlimited plan
2    Electric Bill       $85.50     12/15/2024  Upcoming   Utility bill - account #12345
6    Gym Membership      $45.00     12/05/2024  Due Soon   Planet Fitness - basic plan

Enter payment ID to mark as paid: 1
Paid date (MM/DD/YYYY) or press Enter for today: 

Payment marked as paid successfully!
```

## 🔄 **Recurring Payment Magic**
When you mark a recurring payment as paid, the app automatically creates the next one:

```
After marking Netflix as paid on 11/25/2024:
- Netflix (11/28/2024) → Marked as PAID
- Netflix (12/28/2024) → Automatically created for next month!
```

## 🎨 **Key Features Demonstrated:**

### ✅ **Smart Reminders**
- Shows overdue payments immediately
- Highlights payments due within 7 days
- Displays on app startup

### ✅ **Payment Status System**
- **Paid**: Green checkmark, shows paid date
- **Overdue**: Red warning, past due date
- **Due Soon**: Yellow alert, ≤7 days away
- **Upcoming**: Blue info, >7 days away

### ✅ **Recurring Payments**
- Automatically creates next payment
- Supports monthly, quarterly, yearly
- Never forget a subscription again!

### ✅ **Data Persistence**
- Saves to your local machine
- Survives app restarts
- Can be backed up/transferred

### ✅ **User-Friendly Interface**
- Clear numbered menu system
- Helpful prompts and validation
- Easy navigation between features

## 🚀 **How to Get Started:**

1. **Run the app**: `./run.sh` or `dotnet run`
2. **Add your first payment**: Choose option 2
3. **Set up recurring bills**: Mark them as recurring
4. **Check reminders daily**: Use option 6
5. **Mark payments as paid**: Use option 5 immediately after paying

## 💡 **Pro Tips:**
- **Add ALL recurring bills** first (rent, utilities, subscriptions)
- **Use descriptive names** (e.g., "Verizon Phone" not just "Phone")
- **Add notes** for account numbers, confirmation codes
- **Check reminders** every time you open the app
- **Mark as paid immediately** after paying to keep records current

---

**🎯 Never pay double again with Payment Tracker!**
Your personal financial assistant that keeps you organized and on time! 💪