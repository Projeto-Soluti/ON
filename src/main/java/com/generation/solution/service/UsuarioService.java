package com.generation.solution.service;

	import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.solution.model.Usuario;
import com.generation.solution.model.UsuarioLogin;
import com.generation.solution.repository.UsuarioRepository;

	
		@Service
		public class UsuarioService {

			@Autowired
			private UsuarioRepository usuarioRepository;

			public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

				if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
					
					return Optional.empty();
				/* else */ usuario.setSenha(criptografarSenha(usuario.getSenha())); 
				
				return Optional.of(usuarioRepository.save(usuario)); 
			}

			public Optional<Usuario> atualizarUsuario(Usuario usuario) {

				if (usuarioRepository.findById(usuario.getId()).isPresent()) {
					Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario()); 																										// já
																											
																																																					// pro meu
																											

					if ((buscaUsuario.isPresent()) && (buscaUsuario.get().getId() != usuario.getId()))
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

					/* if else */ usuario.setSenha(criptografarSenha(usuario.getSenha())); 																						// criptografia tb muda

					return Optional.ofNullable(usuarioRepository.save(usuario)); 																				// ou

				}

				/* else */ return Optional.empty();
			}

			// ***LOGIN

			public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

				Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

				if (usuario.isPresent()) {

					if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

						usuarioLogin.get().setId(usuario.get().getId());
						usuarioLogin.get().setNome(usuario.get().getNome());
						usuarioLogin.get().setFoto(usuario.get().getFoto());
						usuarioLogin.get()
								.setToken(gerarBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
						usuarioLogin.get().setSenha(usuario.get().getSenha());

						/* else */ return usuarioLogin;

					}
				}

				/* else */ return Optional.empty();

			}

			private String criptografarSenha(String senha) {

				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
				return encoder.encode(senha);
			}

			private boolean compararSenhas(String senhaDigitada, String senhaBanco) {

				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

				return encoder.matches(senhaDigitada, senhaBanco);
			}

			private String gerarBasicToken(String usuario, String senha) { 
				String token = usuario + ":" + senha;
				byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
				return "Basic " + new String(tokenBase64);

			}
		}


