using Lab8_CSharp.model;

namespace Lab8_CSharp.repository;

class EchipeFileRepository:InFileRepository<string,Echipa>
{
    public EchipeFileRepository(string fileName) : base(fileName, EntityToFileMapping.createEchipa)
    {
        
    }
}