#pragma once
#include <QAbstractTableModel>
#include <QBrush>
#include <Qt>
#include <QFont>
#include "activitate.h"
#include <vector>
#include <qdebug.h>
using std::vector;


class MyTableModel :public QAbstractTableModel {
    Q_PROPERTY(int count READ count NOTIFY countChanged)
        Q_DISABLE_COPY(MyTableModel)
        Q_OBJECT
    std::vector<Activitate> act;
public:
    MyTableModel(const std::vector<Activitate>& act) :act{ act } {
    }

    int rowCount(const QModelIndex& parent = QModelIndex()) const override {
        return act.size();
    }
    int columnCount(const QModelIndex& parent = QModelIndex()) const override {
        return 4;
    }
    //Returns the data stored under the given role for the item referred to by the index.
    QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override {
        if (role == Qt::ForegroundRole) {
            auto prod = this->act[index.row()];
            if (prod.get_durata() > 10) {
                return QBrush(Qt::darkGreen);
            }
        }
        if (role == Qt::FontRole) {
            QFont f;
            f.setItalic(index.row() % 10 == 0);
            f.setBold(index.row() % 10 == 3);
            return f;
        }
        if (role == Qt::BackgroundRole) {

            int row = index.row();
            QModelIndex i = index.sibling(index.row(), 1);
            if (i.data().toString() == "a") {
                QBrush bg(Qt::darkRed);
                return bg;
            }
        }

        if (role == Qt::DisplayRole) {

            Activitate p = act[index.row()];
            if (index.column() == 0) {
                return QString::fromStdString(p.get_titlu());
            }
            else if (index.column() == 1) {
                return QString::fromStdString(p.get_descriere());
            }
            else if (index.column() == 2) {
                return QString::fromStdString(p.get_tip());
            }
            else if (index.column() == 3) {
                return QString::number(p.get_durata());
            }
        }

        return QVariant{};
    }

    int count() const {
        return act.size();
    }

    void setAct(const vector<Activitate> act) {
        this->act = act;
        auto topLeft = createIndex(0, 0);
        auto bottomR = createIndex(rowCount(), columnCount());
        //emit countChanged();
        emit dataChanged(topLeft, bottomR);
        emit layoutChanged();
    }

    Qt::ItemFlags flags(const QModelIndex& /*index*/) const {
        return Qt::ItemIsSelectable | Qt::ItemIsEditable | Qt::ItemIsEnabled;
    }

    QVariant headerData(int section, Qt::Orientation orientation, int role) const
    {
        if (role == Qt::DisplayRole)
        {
            if (orientation == Qt::Horizontal) {
                switch (section)
                {
                case 0:
                    return tr("Titlu");
                case 1:
                    return tr("Descriere");
                case 2:
                    return tr("Tip");
                case 3:
                    return tr("Durata");
                default:
                    return QString("");
                }
            }
        }
        return QVariant();
    }
signals:
    void countChanged();
};