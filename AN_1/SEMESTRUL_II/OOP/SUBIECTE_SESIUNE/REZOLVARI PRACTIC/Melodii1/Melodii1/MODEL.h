#include <QAbstractTableModel>
#include <QBrush>
#include <Qt>
#include "domain.h"
#include <vector>
#include <qdebug.h>
#include <QFont>

using std::vector;


class MyTableModel :public QAbstractTableModel {
    std::vector<Melodie> melodii;
    vector<int> ranks;
public:
    MyTableModel(const vector<Melodie>& melodii, const vector<int> ranks) :melodii{ melodii }, ranks{ ranks } {
    }

    int rowCount(const QModelIndex& parent = QModelIndex()) const override {
        return melodii.size();
    }
    int columnCount(const QModelIndex& parent = QModelIndex()) const override {
        return 5;
    }

    QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override {

        if (role == Qt::DisplayRole) {

            Melodie c = melodii[index.row()];
            if (index.column() == 0) {
                return QString::number(c.get_id());
            }
            else if (index.column() == 1) {
                return QString::fromStdString(c.get_titlu());
            }
            else if (index.column() == 2) {
                return QString::fromStdString(c.get_artist());
            }
            else if (index.column() == 3) {
                return QString::number(c.get_rank());
            }
            else if (index.column() == 4) {
                return QString::number(ranks[c.get_rank()]);
            }
        }

        return QVariant{};
    }

    void set_melodii(const vector<Melodie> melodi, vector<int> fr) {
        this->melodii = melodi;
        this->ranks = fr;
        auto topLeft = createIndex(0, 0);
        auto bottomR = createIndex(rowCount(), columnCount());
        emit layoutChanged();
        emit dataChanged(topLeft, bottomR);
    }

    QVariant headerData(int section, Qt::Orientation orientation, int role) const
    {
        if (role == Qt::DisplayRole)
        {
            if (orientation == Qt::Horizontal) {
                switch (section)
                {
                case 0:
                    return tr("ID");
                case 1:
                    return tr("TITLU");
                case 2:
                    return tr("ARTIST");
                case 3:
                    return tr("RANK");
                case 4:
                    return tr("CU ACELASI RANK");
                default:
                    return QString("");
                }
            }
        }
        return QVariant();
    }
};
