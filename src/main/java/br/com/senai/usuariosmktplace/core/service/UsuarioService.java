package br.com.senai.usuariosmktplace.core.service;

import java.security.MessageDigest;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import br.com.senai.usuariosmktplace.core.dao.DaoUsuario;
import br.com.senai.usuariosmktplace.core.dao.FactoryDao;
import br.com.senai.usuariosmktplace.core.domain.Usuario;

public class UsuarioService {

	private DaoUsuario dao;

	// qual a função disso
	public UsuarioService() {
		this.dao = FactoryDao.getInstance().getDaoUsuario();
	}

	public Usuario criarPor(String nomeCompleto, String senha) {

		this.validar(nomeCompleto, senha);
		String login = gerarLoginPor(nomeCompleto);
		String senhaCriptografada = gerarHashDa(senha);

		Usuario novoUsuario = new Usuario(login, senhaCriptografada, nomeCompleto);
		this.dao.inserir(novoUsuario);
		Usuario usarioSalvo = dao.buscarPor(login);

		return usarioSalvo;

	}

	private String removerAcentoDo(String nomeCompleto) {
		return Normalizer.normalize(nomeCompleto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	private List<String> fracionar(String nomeCompleto) {
		List<String> nomeFracionado = new ArrayList<String>();

		if (nomeCompleto != null && !nomeCompleto.isBlank()) {
			String[] partesDoNome = nomeCompleto.split(" ");
			for (String parte : partesDoNome) {

				// perguntar sobre esse boolean
				boolean isNaoContemArtigo = !parte.equals("de") && !parte.equalsIgnoreCase("e")
						&& !parte.equalsIgnoreCase("dos") && !parte.equalsIgnoreCase("da")
						&& !parte.equalsIgnoreCase("das");

				if (isNaoContemArtigo) {
					nomeFracionado.add(parte.toLowerCase());
				}
			}
		}

		return nomeFracionado;
	}

	private String gerarLoginPor(String nomeCompleto) {
		nomeCompleto = removerAcentoDo(nomeCompleto);

		List<String> partesDoNome = fracionar(nomeCompleto);

		String loginGerado = null;
		Usuario usuarioEncontrado = null;

		if (!partesDoNome.isEmpty()) {
			for (int i = 0; i < partesDoNome.size(); i++) {
				if (i > 0) {
					loginGerado = partesDoNome.get(0) + "." + partesDoNome.get(i);
					usuarioEncontrado = dao.buscarPor(loginGerado);
					if (usuarioEncontrado == null) {
						return loginGerado;
					}
				}
			}
			int proximoSequencial = 0;
			String loginDisponivel = null;
			while (usuarioEncontrado != null) {
				loginDisponivel = loginGerado + ++proximoSequencial;
				usuarioEncontrado = dao.buscarPor(loginDisponivel);
			}
			loginGerado = loginDisponivel;
		}

		return loginGerado;
	}

	private String gerarHashDa(String senha) {
		return new DigestUtils(MessageDigestAlgorithms.SHA3_256).digestAsHex(senha);

	}

	@SuppressWarnings("deprecation")
	private void validar(String senha) {
		// biblioteca guava google
		boolean isSenhaInvalida = !Strings.isNullOrEmpty(senha) && senha.length() >= 6 && senha.length() <= 15;

		// biblioteca guava google - lança exceção caso o boolean for true;
		Preconditions.checkArgument(isSenhaInvalida, "A senha é obrigatória e deve conter entre 6 e 15 caracteres");

		boolean isContemLetra = CharMatcher.inRange('a', 'z').countIn(senha.toLowerCase()) > 0;
		boolean isContemNumero = CharMatcher.inRange('0', '9').countIn(senha) > 0;
		boolean isCaracterInvalido = !CharMatcher.javaLetterOrDigit().matchesAllOf(senha);

		Preconditions.checkArgument(isContemLetra && isContemNumero && !isCaracterInvalido,
				"A senha deve conter letras e números");

	}

	private void validar(String nomecompleto, String senha) {
		List<String> parteDoNome = fracionar(nomecompleto);

		boolean isNomeCompleto = parteDoNome.size() > 1;
		boolean isNomeValido = !Strings.isNullOrEmpty(nomecompleto) && isNomeCompleto && nomecompleto.length() >= 5
				&& nomecompleto.length() <= 120;

		Preconditions.checkArgument(isNomeValido,
				"O Nome é obrigatório e deve conter entre 5 e 120 caracteres e conter sobrenome");

		this.validar(senha);
	}

}
