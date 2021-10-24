package cn.myjszl.service.impl;

import cn.myjszl.dao.AccountRepository;
import cn.myjszl.model.Account;
import cn.myjszl.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repository;

    @Override
    public Account getByUserId(String userId) {
        return repository.findAccountByUserId(userId);
    }

    /**
     * 扣减余额，逻辑自己完善
     * 使用@Transactional标注，开启本地事务
     * @param userId 用户唯一id
     * @param money 扣减的钱
     */
    @Transactional
    @Override
    public void deduct(String userId, Long money) {
        //todo 具体逻辑自己完善
        Account account = repository.findAccountByUserId(userId);
        if (Objects.isNull(account))
            throw new RuntimeException();

        account.setMoney(account.getMoney()-money);
        repository.save(account);

    }
}
