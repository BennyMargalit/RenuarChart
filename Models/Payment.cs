using System.ComponentModel.DataAnnotations;

namespace PaymentTracker.Models;

public class Payment
{
    public int Id { get; set; }
    
    [Required]
    [StringLength(100)]
    public string Name { get; set; } = string.Empty;
    
    [Required]
    [Range(0.01, double.MaxValue, ErrorMessage = "Amount must be greater than 0")]
    public decimal Amount { get; set; }
    
    [Required]
    public DateTime DueDate { get; set; }
    
    public DateTime? PaidDate { get; set; }
    
    public bool IsRecurring { get; set; }
    
    public RecurrenceType RecurrenceType { get; set; }
    
    [StringLength(500)]
    public string? Notes { get; set; }
    
    public bool IsPaid => PaidDate.HasValue;
    
    public bool IsOverdue => !IsPaid && DueDate < DateTime.Today;
    
    public int DaysUntilDue => (DueDate - DateTime.Today).Days;
    
    public string Status
    {
        get
        {
            if (IsPaid) return "Paid";
            if (IsOverdue) return "Overdue";
            if (DaysUntilDue <= 7) return "Due Soon";
            return "Upcoming";
        }
    }
}

public enum RecurrenceType
{
    None,
    Monthly,
    Quarterly,
    Yearly
}