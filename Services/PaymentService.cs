using PaymentTracker.Models;
using System.Text.Json;

namespace PaymentTracker.Services;

public class PaymentService
{
    private readonly string _dataFilePath;
    private List<Payment> _payments;
    
    public PaymentService()
    {
        _dataFilePath = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData), "PaymentTracker", "payments.json");
        _payments = new List<Payment>();
        LoadPayments();
    }
    
    public IEnumerable<Payment> GetAllPayments()
    {
        return _payments.OrderBy(p => p.DueDate);
    }
    
    public IEnumerable<Payment> GetUpcomingPayments(int days = 30)
    {
        var cutoffDate = DateTime.Today.AddDays(days);
        return _payments
            .Where(p => !p.IsPaid && p.DueDate <= cutoffDate)
            .OrderBy(p => p.DueDate);
    }
    
    public IEnumerable<Payment> GetOverduePayments()
    {
        return _payments
            .Where(p => p.IsOverdue)
            .OrderBy(p => p.DueDate);
    }
    
    public IEnumerable<Payment> GetDueSoonPayments(int days = 7)
    {
        var cutoffDate = DateTime.Today.AddDays(days);
        return _payments
            .Where(p => !p.IsPaid && p.DueDate <= cutoffDate && p.DueDate >= DateTime.Today)
            .OrderBy(p => p.DueDate);
    }
    
    public Payment? GetPaymentById(int id)
    {
        return _payments.FirstOrDefault(p => p.Id == id);
    }
    
    public Payment AddPayment(Payment payment)
    {
        payment.Id = _payments.Count > 0 ? _payments.Max(p => p.Id) + 1 : 1;
        _payments.Add(payment);
        SavePayments();
        return payment;
    }
    
    public bool UpdatePayment(Payment payment)
    {
        var existingPayment = GetPaymentById(payment.Id);
        if (existingPayment == null) return false;
        
        var index = _payments.IndexOf(existingPayment);
        _payments[index] = payment;
        SavePayments();
        return true;
    }
    
    public bool DeletePayment(int id)
    {
        var payment = GetPaymentById(id);
        if (payment == null) return false;
        
        _payments.Remove(payment);
        SavePayments();
        return true;
    }
    
    public bool MarkAsPaid(int id, DateTime? paidDate = null)
    {
        var payment = GetPaymentById(id);
        if (payment == null) return false;
        
        payment.PaidDate = paidDate ?? DateTime.Today;
        
        // If recurring, create next payment
        if (payment.IsRecurring && payment.RecurrenceType != RecurrenceType.None)
        {
            var nextPayment = CreateNextRecurringPayment(payment);
            _payments.Add(nextPayment);
        }
        
        SavePayments();
        return true;
    }
    
    public IEnumerable<Payment> GetReminders()
    {
        var reminders = new List<Payment>();
        
        // Overdue payments
        reminders.AddRange(GetOverduePayments());
        
        // Due in next 3 days
        reminders.AddRange(GetDueSoonPayments(3));
        
        return reminders.OrderBy(p => p.DueDate);
    }
    
    private Payment CreateNextRecurringPayment(Payment originalPayment)
    {
        var nextDueDate = originalPayment.RecurrenceType switch
        {
            RecurrenceType.Monthly => originalPayment.DueDate.AddMonths(1),
            RecurrenceType.Quarterly => originalPayment.DueDate.AddMonths(3),
            RecurrenceType.Yearly => originalPayment.DueDate.AddYears(1),
            _ => originalPayment.DueDate
        };
        
        return new Payment
        {
            Name = originalPayment.Name,
            Amount = originalPayment.Amount,
            DueDate = nextDueDate,
            IsRecurring = originalPayment.IsRecurring,
            RecurrenceType = originalPayment.RecurrenceType,
            Notes = originalPayment.Notes
        };
    }
    
    private void LoadPayments()
    {
        try
        {
            if (File.Exists(_dataFilePath))
            {
                var json = File.ReadAllText(_dataFilePath);
                _payments = JsonSerializer.Deserialize<List<Payment>>(json) ?? new List<Payment>();
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Error loading payments: {ex.Message}");
            _payments = new List<Payment>();
        }
    }
    
    private void SavePayments()
    {
        try
        {
            var directory = Path.GetDirectoryName(_dataFilePath);
            if (!string.IsNullOrEmpty(directory) && !Directory.Exists(directory))
            {
                Directory.CreateDirectory(directory);
            }
            
            var json = JsonSerializer.Serialize(_payments, new JsonSerializerOptions { WriteIndented = true });
            File.WriteAllText(_dataFilePath, json);
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Error saving payments: {ex.Message}");
        }
    }
}