using PaymentTracker.Services;
using PaymentTracker.UI;

namespace PaymentTracker;

class Program
{
    static void Main(string[] args)
    {
        Console.WriteLine("Welcome to Payment Tracker!");
        Console.WriteLine("Your personal assistant to never pay double again!");
        Console.WriteLine();
        
        try
        {
            var paymentService = new PaymentService();
            var ui = new ConsoleUI(paymentService);
            
            // Show reminders on startup
            var reminders = paymentService.GetReminders();
            if (reminders.Any())
            {
                Console.WriteLine("⚠️  REMINDERS:");
                foreach (var reminder in reminders)
                {
                    var status = reminder.IsOverdue ? "OVERDUE" : $"Due in {reminder.DaysUntilDue} days";
                    Console.WriteLine($"  • {reminder.Name}: ${reminder.Amount:F2} - {status}");
                }
                Console.WriteLine();
            }
            
            ui.Run();
        }
        catch (Exception ex)
        {
            Console.WriteLine($"An error occurred: {ex.Message}");
            Console.WriteLine("Press any key to exit...");
            Console.ReadKey();
        }
    }
}