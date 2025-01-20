
@Query("UPDATE Product p SET p.productName = :newName WHERE p.id = :id")
int updateProductName(@Param("id") Long id, @Param("newName") String newName);

"UPDATE Product p SET p.productName = ?1 WHERE p.productId = ?2"
"UPDATE Product p SET p.productName = ?1 WHERE p.id = ?2"

"UPDATE Product p SET p.productName = :prodName WHERE p.productId = :id"
Product updateProduct(@Param("prodName") String productName, @Param("id") Long id);



@Query("UPDATE Product p SET p.productName = :newName WHERE p.id = :id")

int updateProductName(@Param("id") Long id, @Param("newName") String newName);

@Query("UPDATE Product p SET p.productName = ?1 WHERE p.id = ?2")

int updateProductName(String newName, Long id);

@Query("SELECT e FROM MyEntity e WHERE e.field = ?1")

List<MyEntity> findByField(String field);

Query("DELETE FROM Book b WHERE b.id = :id")

    void deleteBookById(@Param("id") Long id);

@Query("SELECT b FROM Book b WHERE b.title = :title"

    Book findBookByTitle(@Param("title") String title);

@Query("SELECT p FROM Product p WHERE p.productName = ?1")

    Optional<Product> findByProductName(String productName);

@Query("SELECT m FROM Movie m JOIN FETCH m.director WHERE m.director.id = :directorId")

    List<Movie> findMoviesByDirectorId(@Param("directorId") Long directorId);

@Query("SELECT m FROM Movie m JOIN FETCH m.director WHERE m.director.name = :directorName AND m.director.nationality = :nationality")

    List<Movie> findMoviesByDirectorNameAndNationality(@Param("directorName") String directorName, @Param("nationality") String nationality);

 

// ======

@GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productName}")
    public ResponseEntity<Product> getProductByName(@PathVariable String productName) {
        try {
            Product product = productService.getProductByName(productName);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (NoSuchProductException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productName}")
    public ResponseEntity<Product> updateProductByName(@PathVariable String productName, @RequestBody Product productDetails) {
        try {
            Product updatedProduct = productService.updateProductByName(productName, productDetails);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (NoSuchProductException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{productName}")
    public ResponseEntity<Void> deleteProductByName(@PathVariable String productName) {
        try {
            productService.deleteProductByName(productName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchProductException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
@DeleteMapping("/all") 
public ResponseEntity<Void> deleteAllProducts() {
 productService.deleteAllProducts(); 
return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
}
}




________________________________
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductByName(String productName) {
        return productRepository.findByProductName(productName)
                .orElseThrow(() -> new NoSuchProductException("Product not found"));
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProductByName(String productName, Product productDetails) {
        Product product = getProductByName(productName);
        product.setCategory(productDetails.getCategory());
        product.setPrice(productDetails.getPrice());
        product.setQuantityInStock(productDetails.getQuantityInStock());
        return productRepository.save(product);
    }

    public void deleteProductByName(String productName) {
        Product product = getProductByName(productName);
        productRepository.delete(product);
    }
public void deleteAllProducts() { productRepository.deleteAll(); }
}
_________________________________
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.productName = ?1")
    Optional<Product> findByProductName(String productName);
}
 @Query("SELECT m FROM Movie m JOIN FETCH m.director WHERE m.director.id = :directorId")
    List<Movie> findMoviesByDirectorId(@Param("directorId") Long directorId);

    @Query("SELECT m FROM Movie m JOIN FETCH m.director WHERE m.director.name = :directorName")
    List<Movie> findMoviesByDirectorName(@Param("directorName") String directorName);

   @Query("SELECT m FROM Movie m JOIN FETCH m.director WHERE m.director.name = :directorName AND m.director.nationality = :nationality")
    List<Movie> findMoviesByDirectorNameAndNationality(@Param("directorName") String directorName, @Param("nationality") String nationality);
_______________________
public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException(String message) {
        super(message);
    }
}
_________________________
@GetMapping("/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable String title) {
        Book book = bookService.getBookByTitle(title);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
------------------
@Entity
public class Library {
    // implement library entity here
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long libraryId;
    private String name;
    @OneToMany(mappedBy = "library",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Book> books;
@Entity
public class Book {
    // implement book entity here
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String genre;
    private int rating;
    @ManyToOne
@JoinColumn(name = "director_id")
    @JsonIgnore
------------------------
@Autowired
    LibraryRepository libraryRepository;
    @Autowired
    BookRepository bookRepository;

public Library createLibrary(Library library){
    return libraryRepository.save(library);
}
public Book createBook(Long libraryId,Book book){
    Library l=libraryRepository.findById(libraryId).get();
    book.setLibrary(l);
    return bookRepository.save(book);
}
public List<Library> getAllLibraries(){
    return libraryRepository.findAll();

}
public List<Book> getBooksByLibraryName(String libraryName){
    List<Book> books = new ArrayList<>(); 
    List<Library> libraries = libraryRepository.findAll();
     for (Library library : libraries) 
     { 
        if (library.getName().equals(libraryName)) 
        { books.addAll(library.getBooks());
         } 
        } 
        return books; 
    }

}
--------------------------------
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department){
        return new ResponseEntity<>(departmentService.createDepartment(department),HttpStatus.CREATED);
    }
 
    @PostMapping("/{departmentId}/employees")
    public ResponseEntity<Employee> createEmployee(@PathVariable Long departmentId,@RequestBody Employee employee){
        return new ResponseEntity<>(departmentService.createEmployee(departmentId, employee),HttpStatus.CREATED);
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/employees")
    public List<Employee> getEmployeesByDepartmentName(@RequestParam String departmentName) {
        return departmentService.getEmployeesByDepartmentName(departmentName);
    }	
}
----------------------------------------------
rivate final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    public Director getDirectorById(Long id) {
        return directorRepository.findById(id).orElse(null);
    }

    public Director saveDirector(Director director) {
        return directorRepository.save(director);
    }

    public void deleteDirector(Long id) {
        directorRepository.deleteById(id);
    }

    public Director updateDirector(Long id, Director updatedDirector) {
        return directorRepository.findById(id).map(director -> {
            director.setName(updatedDirector.getName());
            director.setNationality(updatedDirector.getNationality());
            return directorRepository.save(director);
        }).orElse(null);
    }

     public Director updateDirector(Long id, Director updatedDirector) {
        Director existingDirector = directorRepository.findById(id).orElse(null);
        if (existingDirector != null) {
            existingDirector.setName(updatedDirector.getName());
            existingDirector.setNationality(updatedDirector.getNationality());
            return directorRepository.save(existingDirector);
        }
        return null;
    }
}
----------------------------------
JpaRepository<User,Long>
List<User>findByFirstName(String firstName);
List<User>findByLastNameContaining(String keyword);
List<User>findByAgeGreaterThan(int age);
List<User>findByGenderAndCity(String gender,String city);
JpaRepository<Product,Long>
List<User>findByPriceGreaterThan(double price);
List<User>findByNameContainingIgnoreCase(String keyword);
}
DELETE FROM MyEntity e WHERE e.id = ?1
void deleteById(Long id);

@Query("SELECT e FROM MyEntity e WHERE e.field = ?1")
List<MyEntity> findByField(String field);
}
Query("DELETE FROM Book b WHERE b.id = :id")

    void deleteBookById(@Param("id") Long id);
 
@Query("SELECT b FROM Book b WHERE b.title = :title")

    Book findBookByTitle(@Param("title") String title);

 
----------------------------------------
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void updateNameById(Long id, String newName) {
        productRepository.findById(id).ifPresent(product -> {
            product.setName(newName);
        });
    }

    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        productRepository.deleteAll();
    }
@Modifying
    @Query("UPDATE Book b SET b.title = :newTitle WHERE b.id = :id")
    void updateBookTitleById(@Param("newTitle") String newTitle, @Param("id") Long id);
}
}
----------------------------------------
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateNameById(@PathVariable Long id, @RequestParam String newName) {
        if (!productService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productService.updateNameById(id, newName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (!productService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        productService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
---------------------------------------------------------------------

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
-----------------------------------------------------------------
@PostMapping
 public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
   Employee emp = es.createEmployee(employee);
   return ResponseEntity.ok(emp);
 }
 @GetMapping("/{id}")
 public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
   Employee emp = es.getEmployeeById(id);
   return ResponseEntity.ok(emp);
 }
 @PutMapping("/{id}")
 public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable Long id) {
   Employee emp = es.updateEmployee(employee, id);
   return ResponseEntity.ok(emp);
 }
 @DeleteMapping("/{id}")
 public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
   es.deleteEmployee(id);
   return ResponseEntity.ok("Employee deleted succesfuly...");
 }
}
 
----------------------------------------------------------------------
@Autowired
    private TutorService tutorService;

    @PostMapping
    public Tutor addTutor(@RequestBody Tutor tutor){
        return tutorService.addTutor(tutor);
    }
    
    //

    @DeleteMapping("/{tutorId}")
    public void deleteTutor(@PathVariable Long tutorId){
        tutorService.deleteTutor(tutorId);
    }

    //

    @GetMapping
    public List<Tutor> getAlltutors(){
        return tutorService.getAllTutors();
    }

    @PutMapping("/{tutorId}")
    public Tutor updateTutor(@PathVariable Long tutorId,@RequestBody Tutor tutor){
        return tutorService.updateTutor(tutorId, tutor);
    }

    //
    
}
-----------------------------------------------------------------------
@RestController
public class ProductController {
    private final ProductService productService;
 
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
 
    @GetMapping("/products/{id}")
    public ResponseEntity<String> getProduct(@PathVariable String id) {
        String product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
 
    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<String> handleNoSuchProductException(NoSuchProductException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
