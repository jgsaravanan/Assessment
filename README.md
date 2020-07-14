Shift Group Mapping
--------------------
Table Name: SHIFT
<table>
<tr>
    <td>ID</td>
    <td>CODE</td>
</tr>
<tr>
    <td>1</td>
    <td>S1</td>
</tr>
<tr>
    <td>2</td>
    <td>S2</td>
</tr>
<tr>
    <td>3</td>
    <td>S3</td>
</tr>
<tr>
    <td>4</td>
    <td>S4</td>
</tr>
<tr>
    <td>5</td>
    <td>S5</td>
</tr>
<tr>
    <td>6</td>
    <td>S6</td>
</tr>
</table>

Table Name: SHIFT_GROUPING

<table>
<tr>
    <td>ID</td>
    <td>SHIFT_CODE</td>
    <td>SHIFT_GROUP_CODE</td>
</tr>
<tr>
    <td>1</td>
    <td>S1</td>
    <td>G1</td>
</tr>
<tr>
    <td>2</td>
    <td>S2</td>
    <td>G1</td>
</tr>
<tr>
    <td>3</td>
    <td>S3</td>
    <td>G1</td>
</tr>
<tr>
    <td>4</td>
    <td>S1</td>
    <td>G2</td>
</tr>
<tr>
    <td>5</td>
    <td>S5</td>
    <td>G2</td>
</tr>
<tr>
    <td>6</td>
    <td>S3</td>
    <td>G2</td>
</tr>
<tr>
    <td>7</td>
    <td>S4</td>
    <td>G3</td>
</tr>
<tr>
    <td>8</td>
    <td>S2</td>
    <td>G3</td>
</tr>
<tr>
    <td>9</td>
    <td>S6</td>
    <td>G3</td>
</tr>

</table>

Table Name: SHIFT_GROUP
<table>
<tr>
    <td>ID</td>
    <td>CODE</td>
</tr>
<tr>
    <td>1</td>
    <td>S1</td>
</tr>
<tr>
    <td>2</td>
    <td>S2</td>
</tr>
<tr>
    <td>3</td>
    <td>S3</td>
</tr>
<tr>
    <td>4</td>
    <td>S4</td>
</tr>
<tr>
    <td>5</td>
    <td>S5</td>
</tr>
<tr>
    <td>6</td>
    <td>S6</td>
</tr>
<tr>
    <td>7</td>
    <td>G1</td>
</tr>
<tr>
    <td>8</td>
    <td>G2</td>
</tr>
<tr>
    <td>6</td>
    <td>G3</td>
</tr>
</table>

SWAGGER URL : http://localhost:8080/shift-app/swagger-ui.html#/

SWAGGER API DOCS: http://localhost:8080/shift-app/v2/api-docs

API: 1

    METHOD: POST
    
    URI : http://localhost:8080/shift-app/create-group-code
    
    DESCRIPTION: Mapping the Shift Group and Code

RequestBody:

    {   
	
            "G1" : ["S1","S2","S3"],
            
            "G2" : ["S1","S5","S3"],
            
            "G3" : ["S4","S2","S6"]
    }

Response Structure

    [
        {
            "id": 1,
            "shiftGroup": {
                "id": 5,
                "code": "G1"
            },
            "shift": {
                "id": 5,
                "code": "S1"
            }
        },
        {
            "id": 2,
            "shiftGroup": {
                "id": 5,
                "code": "G1"
            },
            "shift": {
                "id": 6,
                "code": "S2"
            }
        },
        {
            "id": 3,
            "shiftGroup": {
                "id": 5,
                "code": "G1"
            },
            "shift": {
                "id": 1,
                "code": "S3"
            }
        },
        {
            "id": 4,
            "shiftGroup": {
                "id": 6,
                "code": "G2"
            },
            "shift": {
                "id": 5,
                "code": "S1"
            }
        },
        {
            "id": 5,
            "shiftGroup": {
                "id": 6,
                "code": "G2"
            },
            "shift": {
                "id": 3,
                "code": "S5"
            }
        },
        {
            "id": 6,
            "shiftGroup": {
                "id": 6,
                "code": "G2"
            },
            "shift": {
                "id": 1,
                "code": "S3"
            }
        },
        {
            "id": 7,
            "shiftGroup": {
                "id": 7,
                "code": "G3"
            },
            "shift": {
                "id": 2,
                "code": "S4"
            }
        },
        {
            "id": 8,
            "shiftGroup": {
                "id": 7,
                "code": "G3"
            },
            "shift": {
                "id": 6,
                "code": "S2"
            }
        },
        {
            "id": 9,
            "shiftGroup": {
                "id": 7,
                "code": "G3"
            },
            "shift": {
                "id": 4,
                "code": "S6"
            }
        }
    ]

API: 2

    METHOD: GET
    
    URI : http://localhost:8080/shift-app/shift-belong-to-group?shiftCode=S1&shiftGroupCode=G1
    
    DESCRIPTION: Does the Shift belong to Group
    
    
Response Structure

    {
        "ShiftCode": "S1",
        "ShiftGroupCode": "G1",
        "doesShiftBelongToGroup": true
    }
    
    {
        "ShiftCode": "S1",
        "ShiftGroupCode": "G6",
        "doesShiftBelongToGroup": false
    }
    
    



