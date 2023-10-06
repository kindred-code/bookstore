# Bookstore

An application that finds creates user and the user is able to create books with the access he gets




## Tech Stack

**Tools:** Spring Boot



## Deployment

docker pull michpoli/bookstore 

```bash
  docker run --bookstore some-postgres -e POSTGRES_PASSWORD=12345 -d postgres
```

The are all the files inside to run the spring application  
## API Reference

#### Register

```http
  POST /api/register
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | username |
| `email` | `string` | email |
| `password` | `string` | password |

#### Authenticate

```http
  POST api/login
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `username`      | `string` | username |
| `password`      | `string` | password |

You get a token in case of succeful login to copy for your requests
#### Create Products
```http
  POST api/books
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `title`      | `string` | name of the book |
| `author`      | `string` | name of the author |
| `ISBN`      | `string` | ISBN |
| `publishedDate`      | `date` | yyyy/MM/dd |

#### Get All Products
```http
  GET api/books
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |

#### Update a Product
```http
  PUT api/books/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `title`      | `string` | name of the book |
| `author`      | `string` | name of the author |
| `ISBN`      | `string` | ISBN |
| `publishedDate`      | `date` | yyyy/MM/dd |

#### Delete a Product
```http
  DELETE /api/books/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | id of the book |




