using PaymentTracker.Models;
using PaymentTracker.Services;

namespace PaymentTracker.UI;

public class ConsoleUI
{
    private readonly PaymentService _paymentService;
    
    public ConsoleUI(PaymentService paymentService)
    {
        _paymentService = paymentService;
    }
    
    public void Run()
    {
        while (true)
        {
            ShowMainMenu();
            var choice = GetUserChoice();
            
            switch (choice)
            {
                case "1":
                    ShowAllPayments();
                    break;
                case "2":
                    AddNewPayment();
                    break;
                case "3":
                    EditPayment();
                    break;
                case "4":
                    DeletePayment();
                    break;
                case "5":
                    MarkPaymentAsPaid();
                    break;
                case "6":
                    ShowReminders();
                    break;
                case "7":
                    ShowUpcomingPayments();
                    break;
                case "8":
                    ShowOverduePayments();
                    break;
                case "9":
                    ShowPaymentSummary();
                    break;
                case "0":
                    Console.WriteLine("Goodbye! Your payments are saved.");
                    return;
                default:
                    Console.WriteLine("Invalid choice. Please try again.");
                    break;
            }
            
            Console.WriteLine("\nPress any key to continue...");
            Console.ReadKey();
            Console.Clear();
        }
    }
    
    private void ShowMainMenu()
    {
        Console.WriteLine("=== PAYMENT TRACKER ===");
        Console.WriteLine("1. View All Payments");
        Console.WriteLine("2. Add New Payment");
        Console.WriteLine("3. Edit Payment");
        Console.WriteLine("4. Delete Payment");
        Console.WriteLine("5. Mark Payment as Paid");
        Console.WriteLine("6. Show Reminders");
        Console.WriteLine("7. Show Upcoming Payments");
        Console.WriteLine("8. Show Overdue Payments");
        Console.WriteLine("9. Payment Summary");
        Console.WriteLine("0. Exit");
        Console.WriteLine("======================");
        Console.Write("Enter your choice: ");
    }
    
    private string GetUserChoice()
    {
        return Console.ReadLine() ?? "";
    }
    
    private void ShowAllPayments()
    {
        Console.WriteLine("\n=== ALL PAYMENTS ===");
        var payments = _paymentService.GetAllPayments();
        
        if (!payments.Any())
        {
            Console.WriteLine("No payments found.");
            return;
        }
        
        DisplayPaymentsTable(payments);
    }
    
    private void AddNewPayment()
    {
        Console.WriteLine("\n=== ADD NEW PAYMENT ===");
        
        var payment = new Payment();
        
        Console.Write("Payment Name: ");
        payment.Name = Console.ReadLine() ?? "";
        
        if (string.IsNullOrWhiteSpace(payment.Name))
        {
            Console.WriteLine("Payment name cannot be empty.");
            return;
        }
        
        Console.Write("Amount: $");
        if (!decimal.TryParse(Console.ReadLine(), out var amount) || amount <= 0)
        {
            Console.WriteLine("Invalid amount. Please enter a positive number.");
            return;
        }
        payment.Amount = amount;
        
        Console.Write("Due Date (MM/DD/YYYY): ");
        if (!DateTime.TryParse(Console.ReadLine(), out var dueDate))
        {
            Console.WriteLine("Invalid date format. Please use MM/DD/YYYY.");
            return;
        }
        payment.DueDate = dueDate;
        
        Console.Write("Is this a recurring payment? (y/n): ");
        var isRecurring = Console.ReadLine()?.ToLower() == "y";
        payment.IsRecurring = isRecurring;
        
        if (isRecurring)
        {
            Console.WriteLine("Recurrence type:");
            Console.WriteLine("1. Monthly");
            Console.WriteLine("2. Quarterly");
            Console.WriteLine("3. Yearly");
            Console.Write("Enter choice: ");
            
            var recurrenceChoice = Console.ReadLine();
            payment.RecurrenceType = recurrenceChoice switch
            {
                "1" => RecurrenceType.Monthly,
                "2" => RecurrenceType.Quarterly,
                "3" => RecurrenceType.Yearly,
                _ => RecurrenceType.Monthly
            };
        }
        
        Console.Write("Notes (optional): ");
        payment.Notes = Console.ReadLine();
        
        var addedPayment = _paymentService.AddPayment(payment);
        Console.WriteLine($"Payment '{addedPayment.Name}' added successfully with ID: {addedPayment.Id}");
    }
    
    private void EditPayment()
    {
        Console.WriteLine("\n=== EDIT PAYMENT ===");
        ShowAllPayments();
        
        Console.Write("Enter payment ID to edit: ");
        if (!int.TryParse(Console.ReadLine(), out var id))
        {
            Console.WriteLine("Invalid ID.");
            return;
        }
        
        var payment = _paymentService.GetPaymentById(id);
        if (payment == null)
        {
            Console.WriteLine("Payment not found.");
            return;
        }
        
        Console.WriteLine($"\nEditing payment: {payment.Name}");
        Console.WriteLine("Press Enter to keep current value, or type new value:");
        
        Console.Write($"Name ({payment.Name}): ");
        var name = Console.ReadLine();
        if (!string.IsNullOrWhiteSpace(name))
            payment.Name = name;
        
        Console.Write($"Amount (${payment.Amount}): ");
        var amountStr = Console.ReadLine();
        if (decimal.TryParse(amountStr, out var amount) && amount > 0)
            payment.Amount = amount;
        
        Console.Write($"Due Date ({payment.DueDate:MM/dd/yyyy}): ");
        var dateStr = Console.ReadLine();
        if (DateTime.TryParse(dateStr, out var dueDate))
            payment.DueDate = dueDate;
        
        Console.Write($"Notes ({payment.Notes ?? "none"}): ");
        var notes = Console.ReadLine();
        if (notes != null)
            payment.Notes = notes;
        
        if (_paymentService.UpdatePayment(payment))
        {
            Console.WriteLine("Payment updated successfully!");
        }
        else
        {
            Console.WriteLine("Failed to update payment.");
        }
    }
    
    private void DeletePayment()
    {
        Console.WriteLine("\n=== DELETE PAYMENT ===");
        ShowAllPayments();
        
        Console.Write("Enter payment ID to delete: ");
        if (!int.TryParse(Console.ReadLine(), out var id))
        {
            Console.WriteLine("Invalid ID.");
            return;
        }
        
        var payment = _paymentService.GetPaymentById(id);
        if (payment == null)
        {
            Console.WriteLine("Payment not found.");
            return;
        }
        
        Console.Write($"Are you sure you want to delete '{payment.Name}'? (y/n): ");
        var confirm = Console.ReadLine()?.ToLower();
        
        if (confirm == "y")
        {
            if (_paymentService.DeletePayment(id))
            {
                Console.WriteLine("Payment deleted successfully!");
            }
            else
            {
                Console.WriteLine("Failed to delete payment.");
            }
        }
    }
    
    private void MarkPaymentAsPaid()
    {
        Console.WriteLine("\n=== MARK PAYMENT AS PAID ===");
        var unpaidPayments = _paymentService.GetAllPayments().Where(p => !p.IsPaid);
        
        if (!unpaidPayments.Any())
        {
            Console.WriteLine("No unpaid payments found.");
            return;
        }
        
        DisplayPaymentsTable(unpaidPayments);
        
        Console.Write("Enter payment ID to mark as paid: ");
        if (!int.TryParse(Console.ReadLine(), out var id))
        {
            Console.WriteLine("Invalid ID.");
            return;
        }
        
        Console.Write("Paid date (MM/DD/YYYY) or press Enter for today: ");
        var dateStr = Console.ReadLine();
        DateTime? paidDate = null;
        
        if (!string.IsNullOrWhiteSpace(dateStr))
        {
            if (!DateTime.TryParse(dateStr, out var date))
            {
                Console.WriteLine("Invalid date format. Using today's date.");
            }
            else
            {
                paidDate = date;
            }
        }
        
        if (_paymentService.MarkAsPaid(id, paidDate))
        {
            Console.WriteLine("Payment marked as paid successfully!");
        }
        else
        {
            Console.WriteLine("Failed to mark payment as paid.");
        }
    }
    
    private void ShowReminders()
    {
        Console.WriteLine("\n=== REMINDERS ===");
        var reminders = _paymentService.GetReminders();
        
        if (!reminders.Any())
        {
            Console.WriteLine("No reminders at this time.");
            return;
        }
        
        DisplayPaymentsTable(reminders);
    }
    
    private void ShowUpcomingPayments()
    {
        Console.WriteLine("\n=== UPCOMING PAYMENTS (Next 30 days) ===");
        var payments = _paymentService.GetUpcomingPayments(30);
        
        if (!payments.Any())
        {
            Console.WriteLine("No upcoming payments in the next 30 days.");
            return;
        }
        
        DisplayPaymentsTable(payments);
    }
    
    private void ShowOverduePayments()
    {
        Console.WriteLine("\n=== OVERDUE PAYMENTS ===");
        var payments = _paymentService.GetOverduePayments();
        
        if (!payments.Any())
        {
            Console.WriteLine("No overdue payments.");
            return;
        }
        
        DisplayPaymentsTable(payments);
    }
    
    private void ShowPaymentSummary()
    {
        Console.WriteLine("\n=== PAYMENT SUMMARY ===");
        
        var allPayments = _paymentService.GetAllPayments().ToList();
        var paidPayments = allPayments.Where(p => p.IsPaid);
        var unpaidPayments = allPayments.Where(p => !p.IsPaid);
        var overduePayments = allPayments.Where(p => p.IsOverdue);
        var dueSoonPayments = allPayments.Where(p => p.DaysUntilDue <= 7 && !p.IsPaid);
        
        Console.WriteLine($"Total Payments: {allPayments.Count}");
        Console.WriteLine($"Paid: {paidPayments.Count()}");
        Console.WriteLine($"Unpaid: {unpaidPayments.Count()}");
        Console.WriteLine($"Overdue: {overduePayments.Count()}");
        Console.WriteLine($"Due Soon (≤7 days): {dueSoonPayments.Count()}");
        
        if (paidPayments.Any())
        {
            var totalPaid = paidPayments.Sum(p => p.Amount);
            Console.WriteLine($"Total Amount Paid: ${totalPaid:F2}");
        }
        
        if (unpaidPayments.Any())
        {
            var totalUnpaid = unpaidPayments.Sum(p => p.Amount);
            Console.WriteLine($"Total Amount Due: ${totalUnpaid:F2}");
        }
        
        if (overduePayments.Any())
        {
            var totalOverdue = overduePayments.Sum(p => p.Amount);
            Console.WriteLine($"Total Overdue Amount: ${totalOverdue:F2}");
        }
    }
    
    private void DisplayPaymentsTable(IEnumerable<Payment> payments)
    {
        Console.WriteLine($"{"ID",-4} {"Name",-20} {"Amount",-10} {"Due Date",-12} {"Status",-10} {"Notes",-20}");
        Console.WriteLine(new string('-', 80));
        
        foreach (var payment in payments)
        {
            var notes = payment.Notes ?? "";
            if (notes.Length > 18) notes = notes[..18] + "..";
            
            Console.WriteLine($"{payment.Id,-4} {payment.Name,-20} ${payment.Amount,-9:F2} {payment.DueDate:MM/dd/yyyy,-12} {payment.Status,-10} {notes,-20}");
        }
    }
}