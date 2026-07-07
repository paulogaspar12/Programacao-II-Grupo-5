/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficcinagestao.servico;

import oficcinagestao.modelo.User;

/**
 * Serviço de Autenticação.
 */
public class AuthService {
    private final UsuarioService usuarioService;

    public AuthService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public User login(String username, String password) {
        User user = usuarioService.findByUsername(username);
        if (user != null) {
            String hashedInput = User.hashPassword(password);
            if (user.getPassword().equals(hashedInput)) {
                return user;
            }
        }
        return null;
    }
}
