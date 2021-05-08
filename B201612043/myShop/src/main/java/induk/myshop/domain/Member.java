package induk.myshop.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private int no;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
}
