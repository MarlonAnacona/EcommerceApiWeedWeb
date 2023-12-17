# PizzappApi



# Docker run container
1. Create image

        docker build -t mi-aplicacion .

2. Run and deploy container

        docker run -p 8080:8080 mi-aplicacion

# Collection Postman

    https://drive.google.com/file/d/1vGOY3ZhAuUyoYM-z4LKGF-hxxWAsxCfK/view?usp=sharing

# INFO API

openapi: 3.0.0
info:
  title: API de Gestión de Compras y Productos
  version: 1.0.0
  description: API para crear y gestionar compras y productos.
servers:
  - url: 'https://localhost:8080/'

        paths:
        /v1/Product/createProduct:
            post:
            summary: Crea un nuevo producto
            security:
                - bearerAuth: []
            requestBody:
                required: true
                content:
                application/json:
                    schema:
                    $ref: '#/components/schemas/ProductDTO'
            responses:
                '201':
                description: Producto creado con éxito
                '400':
                description: Error al crear el producto

        /v1/Product/getProduct/{id}:
            get:
            summary: Obtiene un producto por su ID
            security:
                - bearerAuth: []
            parameters:
                - in: path
                name: id
                required: true
                schema:
                    type: integer
            responses:
                '200':
                description: Detalles del producto
                '400':
                description: Error al obtener el producto

        /v1/Product/getProducts:
            get:
            summary: Lista todos los productos
            security:
                - bearerAuth: []
            responses:
                '200':
                description: Lista de productos
                '400':
                description: Error al obtener los productos

        /v1/Product/getProductCategories/{id}:
            get:
            summary: Lista productos por categoría
            security:
                - bearerAuth: []
            parameters:
                - in: path
                name: id
                required: true
                schema:
                    type: integer
            responses:
                '200':
                description: Lista de productos por categoría
                '400':
                description: Error al obtener los productos

        /v1/Purchase/createPurchase:
            post:
            summary: Crea una nueva compra
            security:
                - bearerAuth: []
            requestBody:
                required: true
                content:
                application/json:
                    schema:
                    $ref: '#/components/schemas/PurchaseDTO'
            parameters:
                - in: header
                name: email
                required: true
                schema:
                    type: string
                    format: email
            responses:
                '201':
                description: Compra realizada con éxito
                '400':
                description: Error al hacer la compra

        /v1/Purchase/getPurchases:
            get:
            summary: Obtiene las compras de un usuario
            security:
                - bearerAuth: []
            responses:
                '200':
                description: Lista de compras del usuario
                '400':
                description: Error al obtener las compras

        components:
        securitySchemes:
            bearerAuth:
            type: http
            scheme: bearer
            bearerFormat: JWT

        schemas:
            ProductDTO:
            type: object
            properties:
                id:
                type: integer
                code:
                type: string
                name:
                type: string
                # Más propiedades del ProductDTO...
            PurchaseDTO:
            type: object
            properties:
                userId:
                type: integer
                dateOfPurchase:
                type: string
                format: date-time
                items:
                type: array
                items:
                    $ref: '#/components/schemas/PurchaseItemDTO'
            PurchaseItemDTO:
            type: object
            properties:
                productId:
                type: integer
                quantity:
                type: integer
                priceAtPurchase:
                type: number
                format: double


# Endpoints
1.  POST /v1/Product/createProduct 
   Request JSON (ProductDTO):

            {
            "code": "P123",
            "name": "Product Name",
            "description": "Description of the product",
            "profilePicture": "base64EncodedImage",
            "price": 100.50,
            "category": 1,
            "quantity": 10,
            "rating": 5
            }
   
Response JSON Success:

    {
    "id": 1,
    "code": "P123",
    "name": "Product Name",
    "description": "Description of the product",
    "profilePicture": "base64EncodedImage",
    "price": 100.50,
    "category": 1,
    "quantity": 10,
    "rating": 5
    }

Error:

    {
    "response": "Error al crear el producto: [Error Message]"
    }
2. GET /v1/Product/getProduct/{id}
    Response JSON Success:

       {
       "id": 1,
       "code": "P123",
       "name": "Product Name",
       "description": "Description of the product",
       "profilePicture": "base64EncodedImage",
       "price": 100.50,
       "category": 1,
       "quantity": 10,
       "rating": 5
       }

   Error:
   
       {
       "response": "Error al obtener el producto: [Error Message]"
       }    

3. GET /v1/Product/getProducts 
   Response JSON Success:
      
       [
       {
       "id": 1,
       "code": "P123",
       "name": "Product Name",
       "description": "Description of the product",
       "profilePicture": "base64EncodedImage",
       "price": 100.50,
       "category": 1,
       "quantity": 10,
       "rating": 5
       },
       {
       "id": 2,
       // More products...
       }
       ]
   
   Error:
   
       {
       "response": "Error al obtener los productos: [Error Message]"
       }

4. GET /v1/Product/getProductCategories/{id}
   Response JSON Success:
   
       [
       {
       "id": 1,
       "code": "P123",
       "name": "Product Name",
       "description": "Description of the product",
       "profilePicture": "base64EncodedImage",
       "price": 100.50,
       "category": 1,
       "quantity": 10,
       "rating": 5
       },
       {
       "id": 2,
       // More products...
       }
       ]

   Error:
 
       {
       "response": "Error al obtener los productos: [Error Message]"
       }

5. POST /v1/Purchase/createPurchase
Request JSON (PurchaseDTO):

       {
       "userId": 123, // This will be set by the server from the JWT token
       "dateOfPurchase": "2023-12-17T15:00:00",
       "items": [
       {
       "productId": 1,
       "quantity": 2,
       "priceAtPurchase": 50.00
       },
       // More items...
       ]
       }
Request Header:
    "email": "user@example.com"

Response JSON Success:

    {
    "response": "Compra realizada con éxito"
    }

Error:

    {
    "response": "Error al hacer la compra: [Error Message]"
    }

