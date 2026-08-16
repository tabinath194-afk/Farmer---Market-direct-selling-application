# Farmer to Market — Direct Selling Platform
### BE CSE Mini Project — Java + JDBC + MySQL + HTML/CSS

This project is a console-based Java application (backed by MySQL) that
lets farmers list produce and customers place orders directly — no
middlemen. A simple static HTML/CSS page (`web/index.html`) is included
as the front-end mockup/demo.

---

## 1. Project Structure

```
FarmerMarket/
│
├── src/
│   ├── Main.java
│   ├── DBConnection.java
│   ├── Farmer.java
│   ├── Product.java
│   ├── Customer.java
│   └── Order.java
│
├── lib/
│   └── mysql-connector-j.jar   (you add this — see step 3)
│
├── web/
│   ├── index.html
│   └── style.css
│
├── database.sql
└── README.md
```

Create this exact folder structure in VS Code:
1. Open VS Code → **File → Open Folder** → create/select an empty folder named `FarmerMarket`.
2. Inside it, create three subfolders: `src`, `lib`, `web`.
3. Place each `.java` file inside `src/`, the two web files inside `web/`,
   and `database.sql` + `README.md` in the project root.

---

## 2. Set Up MySQL

### Start MySQL
- **Windows**: Open "Services" → find `MySQL80` (or similar) → Start.
  Or run `net start MySQL80` in an Admin Command Prompt.
- **macOS**: `brew services start mysql` (if installed via Homebrew), or start it from **System Settings → MySQL**.
- **Linux**: `sudo systemctl start mysql` (or `mysqld` service name may vary).

### Create the database and tables
Open a terminal / MySQL Workbench / VS Code MySQL extension and run:

```bash
mysql -u root -p < database.sql
```

(Enter your MySQL root password when prompted — the project assumes
username `root`, password `root`. Change these in `DBConnection.java`
if your setup is different.)

This creates the `farmer_market` database with the `farmers`,
`customers`, `products`, and `orders` tables (with proper
`AUTO_INCREMENT`, `PRIMARY KEY`, and `FOREIGN KEY` constraints).

### Verify it worked
```sql
mysql -u root -p
USE farmer_market;
SHOW TABLES;
DESCRIBE farmers;
```

You should see all four tables listed.

---

## 3. Add the MySQL JDBC Driver (mysql-connector-j.jar)

1. Download the MySQL Connector/J `.jar` from the official site:
   https://dev.mysql.com/downloads/connector/j/
   (Choose "Platform Independent" → download the `.zip` or `.tar.gz`.)
2. Extract it and find the file named something like
   `mysql-connector-j-9.x.x.jar`.
3. Rename it to `mysql-connector-j.jar` (optional, for consistency) and
   place it inside the `FarmerMarket/lib/` folder.

---

## 4. Compile and Run in VS Code

### Recommended: Install the "Extension Pack for Java" in VS Code first
(Search "Extension Pack for Java" by Microsoft in the Extensions panel.)

### Option A — Using the integrated terminal (manual javac/java)

Open a terminal inside the `FarmerMarket` folder and run:

**Windows (cmd/PowerShell):**
```bash
javac -d bin -cp "lib/mysql-connector-j.jar" src/*.java
java -cp "bin;lib/mysql-connector-j.jar" Main
```

**macOS / Linux:**
```bash
javac -d bin -cp "lib/mysql-connector-j.jar" src/*.java
java -cp "bin:lib/mysql-connector-j.jar" Main
```

Note: Windows uses `;` as the classpath separator, macOS/Linux use `:`.

### Option B — Using VS Code's Run button
1. Open `src/Main.java`.
2. VS Code's Java extension should auto-detect the project. If it
   doesn't pick up the jar automatically, add a `.vscode/settings.json`
   file with:
   ```json
   {
     "java.project.referencedLibraries": [
       "lib/**/*.jar"
     ]
   }
   ```
3. Click the **Run** ▷ button above the `main` method, or press
   `F5` / use "Run Java" from the Command Palette.

---

## 5. Test Database Connectivity

Run the app (`java -cp ... Main`). If the menu prints successfully with
no red error text, the app started fine — but that alone doesn't
confirm the DB connection.

To specifically confirm connectivity:
1. Choose menu option **1 (Farmer Registration)** and fill in test data.
2. If it prints `Farmer registered successfully! Farmer ID = 1`, the
   connection, driver, and database are all working correctly.
3. You can double check in MySQL:
   ```sql
   SELECT * FROM farmers;
   ```

If you see a connection error instead, check the **Common Errors**
section below.

---

## 6. Using the Application

Typical test flow:
1. **Farmer Registration** → note the Farmer ID printed.
2. **Customer Registration** → note the Customer ID printed.
3. **Add Product** → use the Farmer ID from step 1.
4. **View Products** → confirm the product appears.
5. **Search Product** → search by name or category keyword.
6. **Place Order** → use the Customer ID and Product ID from above.
7. **View Orders** → see all orders, or filter by Customer ID.
8. **Exit** → closes the application.

---

## 7. The Website (web/index.html)

`web/index.html` + `web/style.css` is a **static front-end demo page**
— it shows what the platform could look like in a browser (home,
products, farmers, how it works, contact sections). It is not wired to
the Java backend (that would require a servlet container or framework,
which this project intentionally avoids per the "core Java only"
requirement). To view it, simply open `web/index.html` in any browser,
or use the VS Code "Live Server" extension for auto-reload.

---

## 8. Common Errors & Solutions

| Error | Cause | Solution |
|---|---|---|
| `ClassNotFoundException: com.mysql.cj.jdbc.Driver` | JDBC driver jar not on classpath | Make sure `-cp` includes `lib/mysql-connector-j.jar` exactly as shown in Step 4 |
| `Communications link failure` | MySQL server isn't running, or wrong host/port | Start MySQL (see Step 2); confirm it's listening on port 3306 |
| `Access denied for user 'root'@'localhost'` | Wrong username/password in `DBConnection.java` | Edit `USERNAME` / `PASSWORD` constants in `DBConnection.java` to match your MySQL setup |
| `Unknown database 'farmer_market'` | You haven't run `database.sql` yet | Run `mysql -u root -p < database.sql` (Step 2) |
| `Cannot add or update a child row: a foreign key constraint fails` | You entered a Farmer ID / Customer ID / Product ID that doesn't exist | Register the farmer/customer first, or check existing IDs with `SELECT id FROM farmers;` |
| `Public Key Retrieval is not allowed` | Newer MySQL 8 default auth plugin issue | Add `?allowPublicKeyRetrieval=true&useSSL=false` to the JDBC URL in `DBConnection.java`, e.g. `jdbc:mysql://localhost:3306/farmer_market?allowPublicKeyRetrieval=true&useSSL=false` |
| `error: package com.mysql.cj.jdbc does not exist` (at compile time) | Compiling without `-cp lib/mysql-connector-j.jar` | Always compile with the classpath flag as shown in Step 4 |
| Garbled/incorrect input reading (skips prompts) | Mixing `Scanner.nextInt()` and `.nextLine()` | Already handled in `Main.java` — all input uses `nextLine()` with manual parsing |

---

## 9. Notes for Viva / Submission

- All SQL uses `PreparedStatement` (prevents SQL injection).
- Database credentials are centralized in `DBConnection.java` only.
- Foreign keys enforce that products belong to real farmers, and
  orders belong to real customers and products.
- Try-with-resources is used throughout so `Connection`,
  `PreparedStatement`, and `ResultSet` objects are closed automatically.
