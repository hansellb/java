import com.springexamples.springboot.config.ProductDataSourceConfig;
import com.springexamples.springboot.config.UserDataSourceConfig;
import com.springexamples.springboot.dao.product.ProductRepository;
import com.springexamples.springboot.dao.user.UserRepository;
import com.springexamples.springboot.model.product.Product;
import com.springexamples.springboot.model.user.User;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserDataSourceConfig.class, ProductDataSourceConfig.class })
public class JpaMultipleDbTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional("userTransactionManager")
    public void whenCreatingUser_thenCreated() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("johndoe@test.com");
//        User user = User.builder()
//                .name("John Doe")
//                .email("johndoe@test.com")
//                .build();
        user = userRepository.save(user);

        assertNotNull(userRepository.findById(user.getId()));
    }

    @Test
    @Transactional("productTransactionManager")
    public void whenCreatingProduct_thenCreated() {
        Product product = new Product();
        product.setName("Book");
        product.setId(2);
        product.setPrice(20);
//        Product product = Product.builder()
//                .name("Book")
////                .id(2)
//                .price(20)
//                .build();
        product = productRepository.save(product);

        assertNotNull(productRepository.findById(product.getId()));
    }
}
