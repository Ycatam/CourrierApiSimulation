# Courrier Simulation

This is a REST based API that, simulate through a payload wich are the courrier options and the courrier value for each option. 

Due to implementation requirements, if the values for height, width and weight are outside of limits, an empty list is returned.

The alowed HTTP method for this API is the POST method.

If you are testing this API stand alone, you will need add the profile dev to use the H2 as the local DB.

The purpose of this API isn't to teach how to use Docker, so I'm assuming that you have the basic knowledge of Docker.

Tou can also use the docker composer to run this API.

#### docker composer up

To send requistions a body in JSON format is required for example:

    {
        "dimensao": {
                    "altura":102,
                    "largura":40
                     },
        "peso":400
    }

The output will be, for the example above:

    [
	    {
        "nome":"Entrega Ninja",
    	  "valor_frete": 12.00,
    	  "prazo_dias": 6
	    },
	    {
    	  "nome":"Entrega KaBuM",
    	  "valor_frete": 8.00,
    	  "prazo_dias": 4
	    }
    ]

The name of the fields in the JSON payload needs to be like in the example, otherwise will return error.

#### The maximum limit is:
    Height (that means altura) is 200
    Width (taht means largura) is 140

#### The minimum limit is:
    Height is 5
    Width is 6

For the weight (that means peso) don't have a maximum limit, but it can't be zero or negative, this will return a empty list of allowed courriers.

#### The exposed endpoint is:
    localhost:8080/calculofrete

#### The HTTP resonse code for all cases is:
    200 (OK)