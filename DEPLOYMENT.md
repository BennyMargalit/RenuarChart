# 🚀 מדריך להעלאה לרשת - אתר אריאל פרג'

## 📋 **שלבים להעלאה לרשת:**

### **אפשרות 1: Netlify (מומלץ - חינמי)**

#### שלב 1: יצירת חשבון
1. היכנס ל: https://app.netlify.com/signup
2. צור חשבון חינמי
3. אשר את האימייל שלך

#### שלב 2: העלאה ידנית
1. היכנס ל: https://app.netlify.com/
2. לחץ על "New site from Git" או "Deploy manually"
3. גרור את כל הקבצים מהתיקייה (index.html, styles.css, script.js)
4. לחץ על "Deploy site"

#### שלב 3: הגדרת דומיין
1. האתר יקבל כתובת כמו: `https://random-name.netlify.app`
2. לחץ על "Site settings" → "Change site name"
3. שנה לשם: `ariel-pilates` או `ariel-fargi-pilates`

### **אפשרות 2: Vercel (חינמי ומהיר)**

#### שלב 1: יצירת חשבון
1. היכנס ל: https://vercel.com/signup
2. צור חשבון חינמי
3. אשר את האימייל שלך

#### שלב 2: העלאה
1. היכנס ל: https://vercel.com/new
2. לחץ על "Upload" או "Import Git Repository"
3. העלה את כל הקבצים
4. לחץ על "Deploy"

### **אפשרות 3: GitHub Pages (חינמי)**

#### שלב 1: יצירת Repository
1. היכנס ל: https://github.com/new
2. צור repository חדש בשם: `ariel-pilates-website`
3. השאר אותו public

#### שלב 2: העלאת קבצים
```bash
# בטרמינל המקומי שלך (לא בסביבת Docker)
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/username/ariel-pilates-website.git
git push -u origin main
```

#### שלב 3: הפעלת GitHub Pages
1. היכנס ל-repository שלך
2. לחץ על "Settings" → "Pages"
3. תחת "Source" בחר "Deploy from a branch"
4. בחר branch "main" ו-folder "/ (root)"
5. לחץ על "Save"

### **אפשרות 4: Firebase Hosting (חינמי מ-Google)**

#### שלב 1: יצירת חשבון
1. היכנס ל: https://console.firebase.google.com/
2. צור פרויקט חדש
3. הוסף את האתר שלך

#### שלב 2: העלאה
```bash
# בטרמינל המקומי שלך
npm install -g firebase-tools
firebase login
firebase init hosting
firebase deploy
```

## 🌍 **אחרי ההעלאה:**

### **הגדרת דומיין מותאם אישית:**
1. רכוש דומיין (למשל: `arielpilates.co.il`)
2. הגדר DNS records להפנות ל-Netlify/Vercel
3. הוסף את הדומיין בהגדרות האתר

### **הגדרת SSL:**
- Netlify ו-Vercel מספקים SSL אוטומטי
- GitHub Pages מספק SSL אוטומטי
- Firebase מספק SSL אוטומטי

### **בדיקת האתר:**
1. בדוק שהאתר עובד על כל המכשירים
2. בדוק שהטופס עובד
3. בדוק שהניווט חלק
4. בדוק שהאנימציות עובדות

## 📱 **בדיקות חשובות:**

- [ ] האתר נפתח בדפדפן
- [ ] הניווט עובד על כל הסעיפים
- [ ] הטופס שולח הודעות
- [ ] האתר נראה טוב על מובייל
- [ ] האנימציות עובדות
- [ ] התמונות נטענות

## 🔧 **פתרון בעיות נפוצות:**

### **האתר לא נפתח:**
- בדוק שכל הקבצים הועלו
- בדוק שהקובץ הראשי נקרא `index.html`

### **העיצוב לא נטען:**
- בדוק שקובץ `styles.css` הועלה
- בדוק שהנתיב לקובץ נכון

### **הפונקציונליות לא עובדת:**
- בדוק שקובץ `script.js` הועלה
- בדוק שאין שגיאות JavaScript

## 💡 **טיפים חשובים:**

1. **שמור על גיבוי** של כל הקבצים
2. **בדוק את האתר** לפני העלאה סופית
3. **עדכן את פרטי התקשרות** לפני העלאה
4. **הוסף תמונות אמיתיות** במקום ה-placeholders
5. **בדוק שהאתר עובד על כל הדפדפנים**

## 📞 **עזרה נוספת:**

אם אתה נתקל בבעיות:
1. בדוק את הודעות השגיאה בדפדפן (F12)
2. בדוק את הלוגים בפלטפורמת ההעלאה
3. פנה לתמיכה של הפלטפורמה שבחרת

---

**בהצלחה בהעלאת האתר! 🎉**
*אריאל פרג' - מאמנת פילאטיס מקצועית*