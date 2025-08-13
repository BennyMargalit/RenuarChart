# 🎯 PaymentApp - Web API

A modern C# Web API built with .NET 8 and Entity Framework Core to help you track monthly payments, avoid double payments, and never miss a due date again!

## 🚀 **Features**

- **RESTful API**: Full CRUD operations for payment management
- **Smart Reminders**: Get alerts for overdue payments and upcoming due dates
- **Recurring Payments**: Automatically create next payment for recurring bills
- **Payment History**: Keep track of all paid and unpaid payments
- **Database Persistence**: SQLite database with Entity Framework Core
- **Swagger Documentation**: Interactive API documentation
- **Async Operations**: High-performance async/await pattern
- **Data Validation**: Input validation and error handling

## 🏗️ **Architecture**

- **.NET 8 Web API** with minimal hosting model
- **Entity Framework Core** with SQLite database
- **Repository Pattern** with service layer
- **DTOs** for clean API contracts
- **Dependency Injection** for loose coupling
- **CORS** enabled for cross-origin requests

## 📋 **API Endpoints**

### **Core Payment Operations**
- `GET /api/payments` - Get all payments
- `GET /api/payments/{id}` - Get payment by ID
- `POST /api/payments` - Create new payment
- `PUT /api/payments/{id}` - Update existing payment
- `DELETE /api/payments/{id}` - Delete payment

### **Payment Status & Queries**
- `GET /api/payments/upcoming?days=30` - Get upcoming payments
- `GET /api/payments/overdue` - Get overdue payments
- `GET /api/payments/due-soon?days=7` - Get payments due soon
- `GET /api/payments/reminders` - Get payment reminders
- `GET /api/payments/summary` - Get payment statistics

### **Payment Actions**
- `POST /api/payments/{id}/mark-as-paid` - Mark payment as paid

## 🛠️ **Getting Started**

### **Prerequisites**
- .NET 8.0 SDK or later
- Any modern web browser
- API testing tool (Postman, Insomnia, or curl)

### **Installation & Running**

1. **Clone or download** the project files
2. **Open terminal/command prompt** in the project directory
3. **Restore dependencies**:
   ```bash
   dotnet restore
   ```
4. **Build the project**:
   ```bash
   dotnet build
   ```
5. **Run the application**:
   ```bash
   dotnet run
   ```
6. **Open your browser** and navigate to:
   - **API**: `https://localhost:7001/api/payments`
   - **Swagger UI**: `https://localhost:7001/swagger`

## 📊 **Sample API Usage**

### **Create a New Payment**
```bash
curl -X POST "https://localhost:7001/api/payments" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Rent",
    "amount": 1200.00,
    "dueDate": "2024-12-01T00:00:00",
    "isRecurring": true,
    "recurrenceType": 1,
    "notes": "Monthly rent payment"
  }'
```

### **Get All Payments**
```bash
curl "https://localhost:7001/api/payments"
```

### **Get Payment Reminders**
```bash
curl "https://localhost:7001/api/payments/reminders"
```

### **Mark Payment as Paid**
```bash
curl -X POST "https://localhost:7001/api/payments/1/mark-as-paid" \
  -H "Content-Type: application/json" \
  -d '{
    "paidDate": "2024-11-25T00:00:00"
  }'
```

## 🗄️ **Database Schema**

The app uses SQLite with the following structure:

```sql
Payments Table:
- Id (Primary Key)
- Name (Required, max 100 chars)
- Amount (Required, decimal)
- DueDate (Required, datetime)
- PaidDate (Nullable, datetime)
- IsRecurring (boolean)
- RecurrenceType (enum: None, Monthly, Quarterly, Yearly)
- Notes (max 500 chars)
```

## 🔄 **Recurring Payment Logic**

When you mark a recurring payment as paid:
1. **Monthly**: Next payment due in 1 month
2. **Quarterly**: Next payment due in 3 months  
3. **Yearly**: Next payment due in 1 year

The system automatically creates the next payment entry with the same details.

## 📱 **Client Integration**

This API can be consumed by:
- **Web Applications** (React, Angular, Vue.js)
- **Mobile Apps** (React Native, Xamarin, Flutter)
- **Desktop Applications** (WPF, WinForms)
- **Other Services** (microservices, automation scripts)

## 🧪 **Testing the API**

### **Using Swagger UI**
1. Navigate to `/swagger` in your browser
2. Explore available endpoints
3. Test API calls directly from the browser
4. View request/response schemas

### **Using Postman**
1. Import the API endpoints
2. Set base URL to `https://localhost:7001`
3. Test all CRUD operations
4. Verify response formats

## 🔧 **Configuration**

### **Database Connection**
The app uses SQLite by default. To change to another database:

1. Update `appsettings.json` connection string
2. Install appropriate EF Core provider
3. Update `Program.cs` configuration

### **CORS Policy**
Currently allows all origins for development. For production:
1. Restrict allowed origins in `Program.cs`
2. Configure specific headers and methods
3. Enable authentication/authorization

## 🚀 **Deployment**

### **Local Development**
- Uses HTTPS development certificate
- SQLite database file in project root
- Swagger enabled for development

### **Production Deployment**
- Use production database (SQL Server, PostgreSQL)
- Configure proper CORS policies
- Enable HTTPS and security headers
- Set up logging and monitoring

## 📈 **Future Enhancements**

- **Authentication & Authorization** (JWT, OAuth)
- **Email/SMS Notifications** for due dates
- **Payment Categories & Tags**
- **Export to CSV/PDF**
- **Multiple Currency Support**
- **Payment Splitting** for shared expenses
- **Integration with Banking APIs**
- **Real-time Notifications** (SignalR)

## 🐛 **Troubleshooting**

- **Port conflicts**: Change ports in `launchSettings.json`
- **Database issues**: Delete `PaymentApp.db` to reset
- **Build errors**: Ensure .NET 8 SDK is installed
- **CORS issues**: Check browser console for errors

## 🤝 **Contributing**

Feel free to suggest improvements or report issues. This is designed to solve real payment tracking problems with modern web technologies.

---

**🎯 Never pay double again with PaymentApp Web API!**
Your modern, scalable payment management solution! 💪