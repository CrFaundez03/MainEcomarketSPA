package com.Usuario.usuario.Service;

import com.Usuario.usuario.Model.Usuario;
import com.Usuario.usuario.Repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuario;
    private final Long ID = 1L;
    private final String NOMBRE = "Juan Pérez";
    private final String CORREO = "juan@example.com";
    private final String CLAVE = "password123";
    private final String TELEFONO = "5551234567";

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(ID);
        usuario.setNombre(NOMBRE);
        usuario.setCorreo(CORREO);
        usuario.setClave(CLAVE);
        usuario.setTelefono(TELEFONO);
    }

    @Test
    void obtenerTodos() {
        // Arrange
        Usuario usuario2 = new Usuario(2L, "María García", "maria@example.com", "pass456", "5557654321");
        List<Usuario> usuarios = Arrays.asList(usuario, usuario2);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // Act
        List<Usuario> resultado = usuarioService.obtenerTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId() {
        // Arrange
        when(usuarioRepository.findById(ID)).thenReturn(Optional.of(usuario));

        // Act
        Optional<Usuario> resultado = usuarioService.obtenerPorId(ID);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(ID, resultado.get().getId());
        assertEquals(NOMBRE, resultado.get().getNombre());
        verify(usuarioRepository, times(1)).findById(ID);
    }

    @Test
    void obtenerPorId_NoEncontrado() {
        // Arrange
        when(usuarioRepository.findById(ID)).thenReturn(Optional.empty());

        // Act
        Optional<Usuario> resultado = usuarioService.obtenerPorId(ID);

        // Assert
        assertFalse(resultado.isPresent());
        verify(usuarioRepository, times(1)).findById(ID);
    }

    @Test
    void crear() {
        // Arrange
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Act
        Usuario resultado = usuarioService.crear(usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals(ID, resultado.getId());
        assertEquals(NOMBRE, resultado.getNombre());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void actualizar() {
        // Arrange
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("Juan Pérez Actualizado");
        usuarioActualizado.setCorreo("juan.nuevo@example.com");
        usuarioActualizado.setClave("nuevapass");
        usuarioActualizado.setTelefono("5559876543");

        when(usuarioRepository.save(usuarioActualizado)).thenReturn(usuarioActualizado);

        // Act
        Usuario resultado = usuarioService.actualizar(ID, usuarioActualizado);

        // Assert
        assertNotNull(resultado);
        assertEquals(ID, resultado.getId());
        assertEquals("Juan Pérez Actualizado", resultado.getNombre());
        assertEquals("juan.nuevo@example.com", resultado.getCorreo());
        verify(usuarioRepository, times(1)).save(usuarioActualizado);
    }

    @Test
    void eliminar() {
        // Arrange - no se necesita when() porque el método es void

        // Act
        usuarioService.eliminar(ID);

        // Assert
        verify(usuarioRepository, times(1)).deleteById(ID);
    }

    @Test
    void login_CredencialesCorrectas() {
        // Arrange
        when(usuarioRepository.findByCorreoAndClave(CORREO, CLAVE))
                .thenReturn(Optional.of(usuario));

        // Act
        Optional<Usuario> resultado = usuarioService.login(CORREO, CLAVE);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(CORREO, resultado.get().getCorreo());
        assertEquals(CLAVE, resultado.get().getClave());
        verify(usuarioRepository, times(1)).findByCorreoAndClave(CORREO, CLAVE);
    }

    @Test
    void login_CredencialesIncorrectas() {
        // Arrange
        when(usuarioRepository.findByCorreoAndClave("correo@incorrecto.com", "claveincorrecta"))
                .thenReturn(Optional.empty());

        // Act
        Optional<Usuario> resultado = usuarioService.login("correo@incorrecto.com", "claveincorrecta");

        // Assert
        assertFalse(resultado.isPresent());
        verify(usuarioRepository, times(1))
                .findByCorreoAndClave("correo@incorrecto.com", "claveincorrecta");
    }
}