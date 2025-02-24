// package com.betagames.configuration.jwt;

// import java.io.IOException;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Component;
// import static org.springframework.util.StringUtils.hasText;
// import org.springframework.web.filter.OncePerRequestFilter;

// import com.betagames.configuration.service.CustomUserDetailsService;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// /**
//  * La classe JwtFilter è un filtro di sicurezza che gestisce l'autenticazione
//  * degli utenti utilizzando i token JWT (JSON Web Token).
//  * Estende OncePerRequestFilter per garantire
//  * che il filtro venga eseguito una sola volta per ogni richiesta.
//  * 
//  * @author FabriniMarco
//  */
// @Component
// public class JwtFilter extends OncePerRequestFilter {

//     // Provider per la gestione dei token JWT
//     @Autowired
//     JwtProvider jwtProvider;

//     // Servizio per il caricamento dei dettagli dell'utente
//     @Autowired
//     CustomUserDetailsService customUserDetailsService;

//     // Costante per l'intestazione di autorizzazione
//     private static final String AUTHORIZATION_HEADER = "Authorization";
//     // Tipo di autorizzazione utilizzato (Bearer)
//     private static final String AUTH_TYPE = "Bearer ";
//     // Indice per estrarre il token dall'intestazione
//     private static final int TOKEN_START = 7;

//     // Costruttore che inietta il JwtProvider
//     public JwtFilter(JwtProvider jwtProvider) {
//         this.jwtProvider = jwtProvider;
//     }

//     /**
//      * Metodo principale che gestisce il filtraggio delle richieste.
//      * 
//      * @param request     La richiesta HTTP
//      * @param response    La risposta HTTP
//      * @param filterChain La catena di filtri
//      * @throws ServletException Se si verifica un errore durante il filtraggio
//      * @throws IOException      Se si verifica un errore di input/output
//      */
//     @SuppressWarnings("null")
//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {

//         // Controlla se l'endpoint è pubblico
//         if (isPublicEndpoint(request.getRequestURI())) {
//             filterChain.doFilter(request, response); // Passa la richiesta al prossimo filtro
//             return; // Esce dal metodo se l'endpoint è pubblico
//         }

//         // Estrae il token dalla richiesta
//         var token = getTokenFromRequest(request);

//         // Se il token è presente, procede con l'autenticazione
//         if (token != null) {
//             // Estrae il nome utente dal token
//             var usernameFromToken = jwtProvider.getUsernameFromToken(token);
//             // Carica i dettagli dell'utente dal servizio
//             var userDetails = customUserDetailsService.loadUserByUsername(usernameFromToken);
//             // Crea un oggetto di autenticazione
//             var auth = UsernamePasswordAuthenticationToken.authenticated(userDetails, userDetails.getPassword(),
//                     userDetails.getAuthorities());
//             // Imposta l'autenticazione nel contesto di sicurezza
//             SecurityContextHolder.getContext().setAuthentication(auth);
//         }

//         // Passa la richiesta al prossimo filtro nella catena
//         filterChain.doFilter(request, response);
//     } // doFilterInternal

//     /**
//      * Estrae il token JWT dall'intestazione della richiesta.
//      * 
//      * @param httpReq La richiesta HTTP
//      * @return Il token JWT se presente, altrimenti null
//      */
//     private String getTokenFromRequest(HttpServletRequest httpReq) {
//         // Ottiene il valore dell'intestazione di autorizzazione
//         var bearer = httpReq.getHeader(AUTHORIZATION_HEADER);

//         // Se l'intestazione è valida, estrae il token
//         if (validateBearer(bearer)) {
//             return bearer.substring(TOKEN_START); // Rimuove il prefisso "Bearer "
//         }
//         return null; // Restituisce null se non è valido
//     } // getTokenFromRequest

//     /**
//      * Valida l'intestazione di autorizzazione per assicurarsi che contenga un token
//      * valido.
//      * 
//      * @param bearer L'intestazione di autorizzazione
//      * @return true se l'intestazione è valida, false altrimenti
//      */
//     private boolean validateBearer(String bearer) {
//         // Controlla se l'intestazione non è vuota e inizia con "Bearer "
//         return hasText(bearer) && bearer.startsWith(AUTH_TYPE);
//     } // validateBearer

//     /**
//      * Controlla se l'endpoint richiesto è pubblico.
//      * 
//      * @param requestURI L'URI della richiesta
//      * @return true se l'endpoint è pubblico, false altrimenti
//      */
//     private boolean isPublicEndpoint(String requestURI) {
//         // Verifica se l'URI inizia con "/rest/public/"
//         return requestURI.startsWith("/rest/public/");
//     } // isPublicEndpoint

// } // class