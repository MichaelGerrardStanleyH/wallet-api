package com.michael.walletapi.service.Impl;

import com.michael.walletapi.model.*;
import com.michael.walletapi.model.dto.TokenResponse;
import com.michael.walletapi.model.dto.TransactionDTO;
import com.michael.walletapi.model.dto.UserDTO;
import com.michael.walletapi.model.dto.WalletDTO;
import com.michael.walletapi.repository.AddressRepository;
import com.michael.walletapi.repository.UserRepository;
import com.michael.walletapi.security.JwtProvider;
import com.michael.walletapi.service.TransactionService;
import com.michael.walletapi.service.UserService;
import com.michael.walletapi.service.WalletService;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final WalletService walletService;

    private final TransactionService transactionService;

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(@Lazy UserRepository userRepository,
                           @Lazy AddressRepository addressRepository,
                           @Lazy WalletService walletService,
                           @Lazy TransactionService transactionService,
                           @Lazy AuthenticationManager authenticationManager,
                           @Lazy JwtProvider jwtProvider,
                           @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.walletService = walletService;
        this.transactionService = transactionService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public LocalDateTime getTimeNow(){
        return LocalDateTime.now();
    }

    public List<User> getAllUser() {
        List<User> listUser = this.userRepository.findAll();

        if(listUser.isEmpty()){
            return null;
        }else{
            return listUser;
        }
    }

    public User getUserById(Long id){
        Optional<User> optionalUser = this.userRepository.findById(id);

        if(optionalUser.isEmpty()){
            return null;
        }else{
            return optionalUser.get();
        }
    }

    public User createUser(UserDTO userDTO) {
        Address newAddress = Address.builder()
                .address(userDTO.getAddress())
                .created_at(getTimeNow())
                .build();

        Address savedAddress = this.addressRepository.save(newAddress);

        User newUser = User.builder().name(userDTO.getName())
                .dob(userDTO.getDob())
                .username(userDTO.getName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .active(true)
                .created_at(getTimeNow())
                .address(savedAddress)
                .build();

        User savedUser = this.userRepository.save(newUser);

        Wallet newWallet = this.walletService.createWallet(savedUser);

        savedUser.setWallet(List.of(newWallet));

        if(savedUser.getId() == null){
            return null;
        }else{
            return savedUser;
        }

    }

    public TokenResponse generateToken(UserDTO req){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getName(),
                            req.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setToken(jwt);
            return tokenResponse;
        }catch (BadCredentialsException e){
            log.error("Bad Credential ", e);
            throw new RuntimeException(e.getMessage(), e);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public User updateUser(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = this.userRepository.findById(id);

        User existUser;

        if (optionalUser.isEmpty()) {
            return null;
        } else {
            existUser = optionalUser.get();
        }

        existUser.setName(userDTO.getName());
        existUser.setDob(userDTO.getDob());

        return this.userRepository.save(existUser);
    }


    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    public Wallet addWallet(Long id, WalletDTO walletDTO) {
        User existUser = this.userRepository.getById(id);

        Wallet newWallet = this.walletService.addWallet(existUser, walletDTO);

        return newWallet;
    }

    public Transaction topUp(Long userId, Long walletId, TransactionDTO transactionDTO) {
        // exception

        User user = this.getUserById(userId);

        if(user == null){
            return null;
        }

        if(this.walletService.getUsersWallet(walletId, user) == null){
            return null;
        }

        return this.walletService.topUp(walletId, transactionDTO);
    }

    public Transaction transfer(Long userId, Long walletId, TransactionDTO transactionDTO) {
        // exception
        User user = this.getUserById(userId);

        if(user == null){
            return null;
        }

        if(this.walletService.getUsersWallet(walletId, user) == null){
            return null;
        }

        return this.walletService.transfer(walletId, transactionDTO);
    }

    public Wallet updateUsersWallet(Long userId, Long walletId, WalletDTO walletDTO) {
        Wallet existWallet = this.getUsersWalletById(userId, walletId);

        if (existWallet != null) {
            return this.walletService.updateUserWallet(walletId, walletDTO);
        }else{
            return null;
        }
    }

    public Wallet getUsersWalletById(Long userId, Long walletId) {
        User user = this.getUserById(userId);

        return this.walletService.getUsersWallet(walletId, user);
    }

    public ResponseEntity<Void> deleteUsersWallet(Long userId, Long walletId){
        if(this.getUsersWalletById(userId, walletId) == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            this.walletService.deleteWalletById(walletId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @Override
    public Transaction getUsersTransaction(Long userId, Long walletId, Long transactionId) {
        User existUser = this.getUserById(userId);
        Wallet existWallet = this.walletService.getUsersWallet(walletId, existUser);
        return this.transactionService.getUsersTransactionById(transactionId, existWallet);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.getDistinctTopByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Username Not Found");
        }

        return user;
    }

}

