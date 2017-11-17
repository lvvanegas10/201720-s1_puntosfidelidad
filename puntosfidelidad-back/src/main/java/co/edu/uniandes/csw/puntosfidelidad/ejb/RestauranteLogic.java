/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.puntosfidelidad.ejb;

import co.edu.uniandes.csw.puntosfidelidad.entities.EventoEntity;
import co.edu.uniandes.csw.puntosfidelidad.entities.ProductoEntity;
import co.edu.uniandes.csw.puntosfidelidad.entities.RestauranteEntity;
import co.edu.uniandes.csw.puntosfidelidad.entities.SucursalEntity;
import co.edu.uniandes.csw.puntosfidelidad.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.puntosfidelidad.persistence.RestaurantePersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author s.cespedes10
 */

@Stateless
public class RestauranteLogic {
    
    private static final Logger LOGGER = Logger.getLogger(RestauranteLogic.class.getName());
    
    @Inject
     private RestaurantePersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
    
    
    public RestauranteEntity createRestaurante(RestauranteEntity entity) throws BusinessLogicException {
        LOGGER.info("Inicia proceso de creación de restaurante");
        // Verifica la regla de negocio que dice que no puede haber dos restaurantes con el mismo NIT
        if (persistence.find(entity.getNit()) != null) {
            throw new BusinessLogicException("Ya existe un restaurante con ese NIT \"" + entity.getNit()+ "\"");
        }
        // Invoca la persistencia para crear la bodega
        persistence.create(entity);
        LOGGER.info("Termina proceso de creación de Restaurante");
        return entity;
    }
    /**
     * 
     * Obtener todos los restaurantes existentes en la base de datos.
     *
     * @return una lista de restaurantes.
     */
    public List<RestauranteEntity> getRestaurantes() {
        LOGGER.info("Inicia proceso de consultar todos los restaurantes");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<RestauranteEntity> restaurantes = persistence.findAll();
        
        return restaurantes;
    
    }

    public RestauranteEntity getRestaurante(String nit) {
       LOGGER.log(Level.INFO, "Inicia proceso de consultar cliente con id={0}", nit);
        RestauranteEntity restaurante = persistence.find(nit);
        if (restaurante == null) {
            LOGGER.log(Level.SEVERE, "El cliente con el id {0} no existe", nit);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar cliente con id={0}", nit);
        return restaurante;

    }
    
    public void removeRestaurante(String nit) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de borrar cliente con id={0}", nit);
        if(persistence.find(nit)==null) {
            throw new BusinessLogicException("El usuario no existe");
        }  
        persistence.delete(nit);
        LOGGER.log(Level.INFO, "Termina proceso de borrar cliente con id={0}", nit);
    }
    
    
    
    public RestauranteEntity actualizarRestaurante(String nit, RestauranteEntity entity  ) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar Restaurante con id={0}", nit);
        if (!nit.equals(entity.getNit())) {
            throw new BusinessLogicException("No es posible cambiar el Nit");
        }
        
        if(entity.getNombre()==null){
            entity.setNombre(entity.getNit());
        }
        RestauranteEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar cliente con id={0}", entity.getNit());
        return newEntity;
    }
    
    
    public List<SucursalEntity> listSucursales(String nit ) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las sucursales del restaurante con usuario  = {0}", nit);
        return getRestaurante(nit).getSucursales();
    }
    
     public SucursalEntity getSucursal(String nit, Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar una sucursal del restaurante con el NIT = {0}", nit);
        List<SucursalEntity> list = getRestaurante(nit).getSucursales();
        SucursalEntity entity = new SucursalEntity();
        entity.setId(id);
        int index = list.indexOf(entity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }
    
     /**
     * Asocia una Sucursal existente a un Restaurante
     *
     * @param nit Identificador de la instancia de cliente
     * @param SucursalId Identificador de la instancia de TarjetaDeCredito      * @return Instancia de TarjetaDeCreditoEntity que fue asociada a cliente
     * @return Instancia de TarjetaDeCreditoEntity buscada 
     */
    public SucursalEntity addSucursales (String nit, Long SucursalId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar un autor al cliente con id = {0}", nit);
        RestauranteEntity RestauranteEntity = getRestaurante(nit);
        SucursalEntity SucursalEntity = new SucursalEntity();
        SucursalEntity.setId(SucursalId);
        RestauranteEntity.getSucursales().add(SucursalEntity);
        return getSucursal(nit, SucursalId);
    }

    /**
     * Remplaza las instancias de Sucursal asociadas a una instancia de Restaurante
     *
     * @param nit Identificador de la instancia de cliente
     * @param list Colección de instancias de TarjetaDeCreditoEntity a asociar a instancia
     * de cliente
     * @return Nueva colección de TarjetaDeCreditoEntity asociada a la instancia de cliente
     * 
     */
    public List<SucursalEntity> replaceSucursales (String nit, List<SucursalEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar una sucursal del restaurante con nit = {0}", nit);
        RestauranteEntity restauranteEntity = getRestaurante(nit);
        restauranteEntity.setSucursales(list);
        return restauranteEntity.getSucursales();
    }

    /**
     * Desasocia una Sucursal existente de un Restaurante existente
     *
     * @param nit Identificador de la instancia del restaurante
     * @param Id Identificador de la instancia de la sucursal
     */
    public void removeSucursales (String nit, Long Id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un autor del cliente con id = {0}", nit);
        RestauranteEntity entity = getRestaurante(nit);
        SucursalEntity sucursalEntity = new SucursalEntity();
        sucursalEntity.setId(Id);
        entity.getSucursales().remove(sucursalEntity);
    }
     
    
    public List<EventoEntity> listEventos(String usuario) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los eventos del restaurante con usuario  = {0}", usuario);
        return getRestaurante(usuario).getEventos();
    }
    
    public EventoEntity getEvento(String nit, String id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar una sucursal del restaurante con el NIT = {0}", nit);
        List<EventoEntity> list = getRestaurante(nit).getEventos();
        SucursalEntity entity = new SucursalEntity();
        entity.setNombre(id);
        int index = list.indexOf(entity);
        if (index >= 0) {
          return list.get(index);
        }
        return null;
    }

    
    /**
     * Asocia un Producto existente a un Restaurante
     *
     * @param nit Identificador de la instancia de cliente
     * @param Id Identificador de la instancia de TarjetaDeCredito      * @return Instancia de TarjetaDeCreditoEntity que fue asociada a cliente
     * @return Instancia de TarjetaDeCreditoEntity buscada 
     */
    public EventoEntity addEvento (String nit, String Id) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar un autor al cliente con id = {0}", nit);
        RestauranteEntity RestauranteEntity = getRestaurante(nit);
        EventoEntity EventoEntity = new EventoEntity();
        EventoEntity.setNombre(Id);
        RestauranteEntity.getEventos().add(EventoEntity);
        return getEvento(nit, Id);
    }
    
    
     public List<ProductoEntity> listProductos(String usuario) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las sucursales del restaurante con usuario  = {0}", usuario);
        return getRestaurante(usuario).getProductos();
    }
     
     public ProductoEntity getProducto(String nit, Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar una sucursal del restaurante con el NIT = {0}", nit);
        List<ProductoEntity> list = getRestaurante(nit).getProductos();
        ProductoEntity entity = new ProductoEntity();
        entity.setId(id);
        int index = list.indexOf(entity);
        if (index >= 0) {
          return list.get(index);
        }
        return null;
    }
     
     /**
     * Asocia un Producto existente a un Restaurante
     *
     * @param nit Identificador de la instancia de cliente
     * @param Id Identificador de la instancia de TarjetaDeCredito      * @return Instancia de TarjetaDeCreditoEntity que fue asociada a cliente
     * @return Instancia de TarjetaDeCreditoEntity buscada 
     */
    public ProductoEntity addProducto (String nit, Long Id) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar un autor al cliente con id = {0}", nit);
        RestauranteEntity RestauranteEntity = getRestaurante(nit);
        ProductoEntity ProductoEntity = new ProductoEntity();
        ProductoEntity.setId(Id);
        RestauranteEntity.getProductos().add(ProductoEntity);
        return getProducto(nit, Id);
    }

    /**
     * Remplaza las instancias de Productos asociadas a una instancia de Restaurante
     *
     * @param nit Identificador de la instancia de cliente
     * @param list Colección de instancias de TarjetaDeCreditoEntity a asociar a instancia
     * de cliente
     * @return Nueva colección de TarjetaDeCreditoEntity asociada a la instancia de cliente
     * 
     */
    public List<ProductoEntity> replaceProductos (String nit, List<ProductoEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar una sucursal del restaurante con nit = {0}", nit);
        RestauranteEntity restauranteEntity = getRestaurante(nit);
        restauranteEntity.setProductos(list);
        return restauranteEntity.getProductos();
    }

    /**
     * Remplaza las instancias de Productos asociadas a una instancia de Restaurante
     *
     * @param nit Identificador de la instancia de cliente
     * @param list Colección de instancias de TarjetaDeCreditoEntity a asociar a instancia
     * de cliente
     * @return Nueva colección de TarjetaDeCreditoEntity asociada a la instancia de cliente
     * 
     */
    public List<EventoEntity> replaceEventos (String nit, List<EventoEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar una sucursal del restaurante con nit = {0}", nit);
        RestauranteEntity restauranteEntity = getRestaurante(nit);
        restauranteEntity.setEventos(list);
        return restauranteEntity.getEventos();
    }
    
    /**
     * Desasocia una Sucursal existente de un Restaurante existente
     *
     * @param nit Identificador de la instancia del restaurante
     * @param Id Identificador de la instancia de la sucursal
     */
    public void removeEventos (String nit, String Id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un autor del cliente con id = {0}", nit);
        RestauranteEntity entity = getRestaurante(nit);
        EventoEntity EventoEntity = new EventoEntity();
        EventoEntity.setNombre(Id);
        entity.getEventos().remove(EventoEntity);
    }
    
    
    
    /**
     * Desasocia una Sucursal existente de un Restaurante existente
     *
     * @param nit Identificador de la instancia del restaurante
     * @param Id Identificador de la instancia de la sucursal
     */
    public void removeProductos (String nit, Long Id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un autor del cliente con id = {0}", nit);
        RestauranteEntity entity = getRestaurante(nit);
        ProductoEntity ProductoEntity = new ProductoEntity();
        ProductoEntity.setId(Id);
        entity.getProductos().remove(ProductoEntity);
    }
}
