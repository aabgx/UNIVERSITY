using Lab8_CSharp.model;

namespace Lab8_CSharp.repository;

class EleviFileRepository:InFileRepository<string,Elev>
{
    public EleviFileRepository(string fileName) : base(fileName, EntityToFileMapping.createElev)
    {
        
    }
}