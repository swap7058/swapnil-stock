package com.iiht.userservice.repo.custom;

import com.iiht.userservice.entities.User;
import com.iiht.userservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import java.util.Optional;


@Repository
public class UserRepoCustomImpl implements UserRepoCustom {
    @Autowired
    @Lazy
    private UserRepo repo;

    @Autowired
    @Lazy
    private EntityManager em;

    @Override
    public void setCondirmedTrue(int userId) {
        User user = repo.findById(userId).get();
        user.setConfirmed(true);
        repo.save(user);
    }
}
