package io.github.pdkst.java.utils;

import io.github.pdkst.model.Address;
import io.github.pdkst.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ContextOptionalTest {

    @Test
    void withContext() {

    }

    @Test
    void map() {
        final ContextOptional<User, Object> userOpt = ContextOptional.ofNull(new User("123", new Address("detail")));
        final String detail = userOpt.map(User::getAddress)
                .map(Address::getAddressDetail)
                .orElse(null);
        assertEquals(detail, "detail");
    }

    @Test
    void mapWithContext() {
        final ContextOptional<User, Address> userOpt = ContextOptional.ofNull(new User("123", null), new Address("detail"));
        final ContextOptional<User, Address> next = userOpt.mapWithContext((user, address) -> {
            user.setAddress(address);
            return user;
        });
        log.info("next = {}", next);
        assertNotNull(userOpt.orElse(null).getAddress());
    }

    @Test
    void ifExistsWithContext() {
        final User user = new User("123", null);
        final Address address = new Address("detail at address");
        final ContextOptional<Address, User> userOpt = ContextOptional.ofNull(address, user);
        userOpt.ifExistsWithContext(User::setAddress);
        log.info("user={}", user);
        assertNotNull(user.getAddress());
    }
}