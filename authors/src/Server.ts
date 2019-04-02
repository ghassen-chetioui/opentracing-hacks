import express = require('express');
import bodyParser = require('body-parser');
import uuid = require('uuid');

class Author {
    readonly id: string;

    constructor(readonly name: string, readonly alias?: string, readonly nationality?: string) {
        this.id = uuid.v4()
    }
}

interface AuthorRepository {
    create(author: Author): Promise<void>
    all(): Promise<Author[]>
    byId(id: string): Promise<Author | undefined>
    delete(id: string): Promise<void>
}

class InMemoryAuthorRepository implements AuthorRepository {

    private db = new Array<Author>()

    async create(author: Author): Promise<void> {
        this.db.push(author)
    }

    async byId(id: string): Promise<Author | undefined> {
        return this.db.find(author => author.id === id);
    }

    async all(): Promise<Author[]> {
        return Object.assign([], this.db);
    }

    async delete(id: string): Promise<void> {
        this.db = this.db.filter(author => author.id !== id)
    }

}

class Server {
    private readonly app: express.Application;
    private readonly repository: AuthorRepository;

    constructor() {
        this.app = express();
        this.repository = new InMemoryAuthorRepository();
    }

    start() {
        this.app.use(bodyParser.json())

        this.app.get('/authors', async (req, res) => {
            res.send(await this.repository.all())
        })

        this.app.get('/authors/:id', async (req, res) => {
            const author = await this.repository.byId(req.params.id)
            if (author) {
                res.send(author)
            } else {
                res.sendStatus(404)
            }

        })

        this.app.post('/authors', async (req, res) => {
            const author = new Author(req.body.name, req.body.alias, req.body.nationality)
            await this.repository.create(author)
            res.sendStatus(201)
        })

        this.app.delete('/authors/:id', async (req, res) => {
            await this.repository.delete(req.params.id)
            res.sendStatus(204)
        })

        this.app.listen(8081)
    }

}

new Server().start()