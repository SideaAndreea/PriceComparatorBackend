# PriceComparatorBackend
 
Overview

This is a application realized in Eclipse with Spring Boot framework and Maven build tool, designed to compare product prices across different stores, provide price history, product recommendations, basket optimization, and custom price alerts for users. The data is loaded from CSV files located in the resources folder.


Project Structure

price-comparator-backend/src/main/java/com/pricecomparator

/controller

REST controllers for exposing API endpoints for products, discounts, recommendations, and alerts.

	ProductController.java
 
	DiscountController.java
 
	BasketController.java
 
	RecommendationController.java
 
	PriceAlertController.java

/model
Data models
	Product.java
	Discount.java
	BasketItem.java
	ProductRecommendation.java
	PriceAlert.java

/service
	CsvLoaderService.java – Loads product data from CSV files.
	DiscountService.java - Handles loading discount data from CSV files, filtering active discounts for a given date, identifying newly started discounts, and retrieving the top discounts sorted by percentage.
	PriceHistoryService.java – Provides historical price data for products.
	RecommendationService.java – Suggests substitute products based on category.
	PriceAlertService.java – Manages user-defined price alerts, persisting them to a CSV file.
	BasketService.java - Optimize a shopping basket by searching for the cheapest matching products across multiple stores on a given date

PriceComparatorApplication.java

price-comparator-backend/src/main/resources/csv/
CSV files 

price-comparator-backend/pom.xml 
Dependencies and configuration


Build and Run Instructions
Prerequisites
Java 17+ installed
Maven installed

Build
mvn clean package

Run
mvn spring-boot:run

Alternatively, run the generated jar:
java -jar target/pricecomparator-0.0.1-SNAPSHOT.jar


Usage and API Endpoints

1. GET Products /store/{store}/date/{date}
http://localhost:8080/api/products/store/lidl/date/2025-05-01

2. GET Active Discounts for a given date /store/{store}/date/{date}
http://localhost:8080/api/discounts/store/lidl/date/2025-05-08

3. GET Newly started discounts /store/{store}/new/{date}
http://localhost:8080/api/discounts/store/profi/new/2025-05-08

4. GET Top Discounts sorted by percentage /store/{store}/top/{date}
http://localhost:8080/api/discounts/store/kaufland/top/2025-05-08

5. POST Basket Optimization /optimize
http://localhost:8080/api/basket/optimize

{
  "items": [
    {"productName": "lapte zuzu", "quantity": 1},
    {"productName": "pâine albă", "quantity": 2}
  ],
  "stores": ["profi", "lidl", "kaufland"],
  "date": "2025-05-08"
}

6. GET Price History 
Returns price points over time for a product in a specific store.
http://localhost:8080/api/products/price-history?productName=lapte%20zuzu&store=kaufland

7. GET Product Substitutes
Returns a list of substitute products in the same category with similar pricing.
http://localhost:8080/api/products/P001/substitutes

8. POST Custom price alert
http://localhost:8080/api/alerts

{
  "userId": "user09",
  "productId": "P001",
  "targetPrice": 9.99,
  "active": true
}

9. GET User price alert
http://localhost:8080/api/alerts/user/user09
