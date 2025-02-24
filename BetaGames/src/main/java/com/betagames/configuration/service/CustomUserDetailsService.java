// package com.betagames.configuration.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

//  import com.betagames.model.Users;
// import com.betagames.repository.IUsersRepository;

// /**
//  * La classe CustomUser DetailsService implementa l'interfaccia
//  * UserDetailsService di Spring Security.
//  * Questa classe è responsabile del caricamento dei dettagli
//  * dell'utente durante il processo di autenticazione.
//  * 
//  * @author FabriniMarco
//  */
// @Service
// public class CustomUserDetailsService implements UserDetailsService {

//     // Repository per l'accesso ai dati degli utenti
//     @Autowired
//     private IUsersRepository userRepository;

//     /**
//      * Carica i dettagli dell'utente in base al nome utente fornito.
//      * 
//      * @param username Il nome utente da cercare.
//      * @return Un oggetto UserDetails contenente le informazioni dell'utente.
//      * @throws UsernameNotFoundException Se l'utente non viene trovato.
//      */
//     @Override
//     @Transactional(readOnly = true) // Indica che il metodo è di sola lettura
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         // Cerca l'utente nel repository in base al nome utente
//         Users user = userRepository.findByUsername(username)
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

//         // Costruisce e restituisce un oggetto UserDetails
//         return User.builder()
//                 .username(user.getUsername()) // Imposta il nome utente
//                 .password(user.getPwd()) // Imposta la password
//                 .roles(user.getRole().getName()) // Imposta i ruoli dell'utente
//                 .build(); // Costruisce l'oggetto UserDetails
//     }// loadUserByUsername

// }// class