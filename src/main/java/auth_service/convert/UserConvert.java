package auth_service.convert;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import auth_service.UserRecord;
import auth_service.expenses_persistence.entity.UserEntity;

@Service
public class UserConvert implements Function<UserEntity, UserRecord> {
    @Override
    public UserRecord apply(UserEntity userEntity) {
        return new UserRecord(userEntity.getId(), userEntity.getUsername());
    }
}
