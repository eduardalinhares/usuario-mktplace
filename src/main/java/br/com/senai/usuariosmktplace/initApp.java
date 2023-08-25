package br.com.senai.usuariosmktplace;

import java.security.Provider.Service;

import br.com.senai.usuariosmktplace.core.dao.DaoUsuario;
import br.com.senai.usuariosmktplace.core.dao.FactoryDao;
import br.com.senai.usuariosmktplace.core.dao.ManagerDb;
import br.com.senai.usuariosmktplace.core.domain.Usuario;
import br.com.senai.usuariosmktplace.core.service.UsuarioService;

public class InitApp {
	
	public static void main(String [] args) {
		
		UsuarioService service = new UsuarioService();
		service.criarPor("Tatiana "
				+ "Machado", "pita123");
	}
}
