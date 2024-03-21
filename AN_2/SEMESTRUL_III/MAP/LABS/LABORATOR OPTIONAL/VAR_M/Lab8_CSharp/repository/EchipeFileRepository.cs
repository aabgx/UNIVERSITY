using Lab8_CSharp.model;
using Lab8_CSharp.model.Validator;

namespace Lab8_CSharp.repository;

class EchipeFileRepository:InFileRepository<string,Echipa>
{
    public EchipeFileRepository(IValidator<Echipa> validator,string fileName) : base(validator,fileName, EntityToFileMapping.createEchipa)
    {
        
    }
}