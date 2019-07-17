package mine.entity;

/**
 * @author 蚂蚁课堂创始人-余胜军QQ644064779
 * @title: UserEntity
 * @description: 每特教育独创第五期互联网架构课程
 * @date 2019/6/2220:55
 */
public class UserEntity {
    private String userName;
    private Integer userId;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userName='" + userName + '\'' +
                ", userId=" + userId +
                '}';
    }

    public UserEntity(String userName, Integer userId) {
		System.out.println("jaizai ");
        this.userName = userName;
        this.userId = userId;
    }
}
