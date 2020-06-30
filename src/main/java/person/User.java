package person;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Description:
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@Data
@AllArgsConstructor
public class User {
    private String username;
    private String password;
}