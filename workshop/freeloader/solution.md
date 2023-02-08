# Solution Fix for FREELOADER Assignment

There is a SQL injection in the `SearchRepository`.
You can fix it by implementing een parameterized queries. The Hibernate implementations uses a prepared statement under the hood to prevent injection.
A possible solution is:

```java
    public List<Product> searchProduct (String input) {
        var lowerInput = input.toLowerCase(Locale.ROOT);
        String querytext = "Select * from Product where lower(description) like CONCAT('%', ?1, '%') OR lower(product_name) like CONCAT('%', ?2, '%')";
        var query = em.createNativeQuery(querytext, Product.class);
        query.setParameter(1, lowerInput);
        query.setParameter(2, lowerInput);
        var resultList = (List<Product>) query.getResultList();
        return resultList;
    }
```